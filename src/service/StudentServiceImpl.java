//package cz.mendelu.service;
//
//import cz.mendelu.domain.Student;
//import cz.mendelu.dto.StudentDTO;
//import cz.mendelu.repository.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class StudentServiceImpl implements StudentService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    private StudentDTO mapToDTO(Student student) {
//        StudentDTO dto = new StudentDTO();
//        dto.setId(student.getId());
//        dto.setName(student.getName());
//        dto.setEmail(student.getEmail());
//        dto.setStudentId(student.getStudentId());
//        return dto;
//    }
//
//    private Student mapToEntity(StudentDTO dto) {
//        Student student = new Student();
//        student.setId(dto.getId());
//        student.setName(dto.getName());
//        student.setEmail(dto.getEmail());
//        student.setStudentId(dto.getStudentId());
//        return student;
//    }
//
//    @Override
//    public StudentDTO createStudent(StudentDTO dto) {
//        Student student = mapToEntity(dto);
//        return mapToDTO(studentRepository.save(student));
//    }
//
//    @Override
//    public StudentDTO updateStudent(Long id, StudentDTO dto) {
//        Student existing = studentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//        existing.setName(dto.getName());
//        existing.setEmail(dto.getEmail());
//        existing.setStudentId(dto.getStudentId());
//        return mapToDTO(studentRepository.save(existing));
//    }
//
//    @Override
//    public void deleteStudent(Long id) {
//        studentRepository.deleteById(id);
//    }
//
//    @Override
//    public StudentDTO getStudentById(Long id) {
//        return studentRepository.findById(id)
//                .map(this::mapToDTO)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//    }
//
//    @Override
//    public List<StudentDTO> getAllStudents() {
//        return studentRepository.findAll()
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//    @Override
//    public boolean existsByStudentId(String studentId) {
//        return studentRepository.findByStudentId(studentId).isPresent();
//    }
//
//    @Override
//    public StudentDTO upsertByStudentId(StudentDTO dto) {
//        // Find existing student
//        Student student = studentRepository.findByStudentId(dto.getStudentId())
//                .orElseGet(Student::new); // create new if not exists
//
//        student.setName(dto.getName());
//        student.setEmail(dto.getEmail());
//        student.setStudentId(dto.getStudentId()); // this must be kept consistent
//        return mapToDTO(studentRepository.save(student));
//    }
//
//}
