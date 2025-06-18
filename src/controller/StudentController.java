package cz.mendelu.controller;

import cz.mendelu.dto.StudentDTO;
import cz.mendelu.service.StudentService;
import cz.mendelu.service.MockApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger; // Import Logger
import org.slf4j.LoggerFactory; // Import LoggerFactory


import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class); // Add this line

    @Autowired
    private StudentService studentService;

    @Autowired
    private MockApiService mockApiService;

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    // ✅ NEW endpoint: get students from Postman Mock Server
    @PostMapping("/mocked")
    public List<StudentDTO> saveOrUpdateAllMockedStudents() {
        logger.info("Received POST request to /api/students/mocked. Attempting to fetch from mock service..."); // <--- UPDATED LOG STATEMENT
        List<StudentDTO> mockStudents = mockApiService.fetchStudentsFromMock();
        return mockStudents.stream()
                .map(studentService::upsertByStudentId)
                .toList();
    }

    @GetMapping("/mocked/{id}")
    public StudentDTO getMockedStudentById(@PathVariable int id) {
        return mockApiService.fetchStudentById(id);
    }
}