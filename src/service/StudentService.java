package cz.mendelu.service;

import cz.mendelu.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    boolean existsByStudentId(String studentId);
    StudentDTO upsertByStudentId(StudentDTO studentDTO);

}
