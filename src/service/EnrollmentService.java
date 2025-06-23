package cz.mendelu.service;
import cz.mendelu.dto.EnrollmentDTO;
import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(EnrollmentDTO dto);
    EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO dto);
    void deleteEnrollment(Long id);
    EnrollmentDTO getEnrollmentById(Long id);
    List<EnrollmentDTO> getAllEnrollments();
    EnrollmentDTO upsertById(EnrollmentDTO dto);
}
