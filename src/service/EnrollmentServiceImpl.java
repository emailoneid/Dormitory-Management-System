package cz.mendelu.service;

import cz.mendelu.domain.Enrollment;
import cz.mendelu.domain.Room;
import cz.mendelu.domain.Student;
import cz.mendelu.dto.AggregateDTO;
import cz.mendelu.dto.EnrollmentDTO;
import cz.mendelu.repository.EnrollmentRepository;
import cz.mendelu.repository.RoomRepository;
import cz.mendelu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    // Map entity to DTO
    private EnrollmentDTO mapToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setStartDate(enrollment.getStartDate());
        dto.setEndDate(enrollment.getEndDate());
        dto.setStudentId(enrollment.getStudent().getStudentId()); // external ID
        dto.setRoomId(enrollment.getRoom().getId());
        dto.setStudentName(enrollment.getStudent().getName());
        dto.setRoomNumber(enrollment.getRoom().getRoomNumber());
        return dto;
    }

    // Map DTO to entity
    private Enrollment mapToEntity(EnrollmentDTO dto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());
        enrollment.setStartDate(dto.getStartDate());
        enrollment.setEndDate(dto.getEndDate());

        Student student = studentRepository.findByStudentId(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        enrollment.setStudent(student);

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        enrollment.setRoom(room);

        return enrollment;
    }

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO dto) {
        Enrollment enrollment = mapToEntity(dto);
        return mapToDTO(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO dto) {
        Enrollment existing = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());

        Student student = studentRepository.findByStudentId(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        existing.setStudent(student);

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        existing.setRoom(room);

        return mapToDTO(enrollmentRepository.save(existing));
    }

    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentDTO upsertById(EnrollmentDTO dto) {
        Enrollment enrollment;

        if (dto.getId() != null) {
            enrollment = enrollmentRepository.findById(dto.getId()).orElse(null);

            if (enrollment != null) {
                enrollment.setStartDate(dto.getStartDate());
                enrollment.setEndDate(dto.getEndDate());

                Student student = studentRepository.findByStudentId(dto.getStudentId())
                        .orElseThrow(() -> new RuntimeException("Student not found"));
                enrollment.setStudent(student);

                Room room = roomRepository.findById(dto.getRoomId())
                        .orElseThrow(() -> new RuntimeException("Room not found"));
                enrollment.setRoom(room);
            } else {
                enrollment = mapToEntity(dto);
                enrollment.setId(null); // prevent detached entity
            }
        } else {
            enrollment = mapToEntity(dto);
        }

        return mapToDTO(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<AggregateDTO> getEnrollmentAggregateView() {
        return enrollmentRepository.findAll().stream().map(enrollment -> {
            AggregateDTO dto = new AggregateDTO();
            dto.setStudentId(enrollment.getStudent().getStudentId());
            dto.setStudentName(enrollment.getStudent().getName());
            dto.setEmail(enrollment.getStudent().getEmail());
            dto.setRoomNumber(enrollment.getRoom().getRoomNumber());
            dto.setStartDate(enrollment.getStartDate());
            dto.setEndDate(enrollment.getEndDate());
            return dto;
        }).collect(Collectors.toList());
    }
}
