package cz.mendelu.controller;
import cz.mendelu.dto.EnrollmentDTO;
import cz.mendelu.service.EnrollmentService;
import cz.mendelu.service.MockApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentDTO createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        return enrollmentService.createEnrollment(enrollmentDTO);
    }

    @PutMapping("/{id}")
    public EnrollmentDTO updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentDTO enrollmentDTO) {
        return enrollmentService.updateEnrollment(id, enrollmentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }

    @GetMapping("/{id}")
    public EnrollmentDTO getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @GetMapping
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @Autowired
    private MockApiService mockApiService;

}
