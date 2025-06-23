package cz.mendelu.service;

import cz.mendelu.domain.Enrollment;
import cz.mendelu.domain.Room;
import cz.mendelu.dto.EnrollmentDTO;
import cz.mendelu.repository.EnrollmentRepository;
import cz.mendelu.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final RoomRepository roomRepository;
    private final MockApiService mockApiService;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 RoomRepository roomRepository,
                                 MockApiService mockApiService) {
        this.enrollmentRepository = enrollmentRepository;
        this.roomRepository = roomRepository;
        this.mockApiService = mockApiService;
    }

    private EnrollmentDTO mapToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setStartDate(enrollment.getStartDate());
        dto.setEndDate(enrollment.getEndDate());
        dto.setStudentId(enrollment.getStudentId());

        // Dynamically fetch student name using external API
        mockApiService.getStudentByStudentId(enrollment.getStudentId())
                .ifPresent(student -> {
                    dto.setStudentName(student.getName());
                    dto.setStudentEmail(student.getEmail());
                });

        Room room = enrollment.getRoom();
        if (room != null) {
            dto.setRoomId(room.getId());
            dto.setRoomNumber(room.getRoomNumber());
        }

        return dto;
    }

    private Enrollment mapToEntity(EnrollmentDTO dto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());
        enrollment.setStartDate(dto.getStartDate());
        enrollment.setEndDate(dto.getEndDate());
        enrollment.setStudentId(dto.getStudentId()); // Only store studentId

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + dto.getRoomId()));
        enrollment.setRoom(room);

        return enrollment;
    }

    @Override
    @Transactional
    public EnrollmentDTO createEnrollment(EnrollmentDTO dto) {
        Enrollment enrollment = mapToEntity(dto);
        return mapToDTO(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO dto) {
        Enrollment existing = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));

        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setStudentId(dto.getStudentId());

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + dto.getRoomId()));
        existing.setRoom(room);

        return mapToDTO(enrollmentRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnrollmentDTO upsertById(EnrollmentDTO dto) {
        Enrollment enrollment;

        if (dto.getId() != null) {
            enrollment = enrollmentRepository.findById(dto.getId()).orElse(null);

            if (enrollment != null) {
                enrollment.setStartDate(dto.getStartDate());
                enrollment.setEndDate(dto.getEndDate());
                enrollment.setStudentId(dto.getStudentId());

                Room room = roomRepository.findById(dto.getRoomId())
                        .orElseThrow(() -> new RuntimeException("Room not found with ID: " + dto.getRoomId()));
                enrollment.setRoom(room);
            } else {
                enrollment = mapToEntity(dto);
                enrollment.setId(null); // Ensure new insert
            }
        } else {
            enrollment = mapToEntity(dto);
        }

        return mapToDTO(enrollmentRepository.save(enrollment));
    }
}
