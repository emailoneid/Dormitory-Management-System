package cz.mendelu.service;

import cz.mendelu.dto.StudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MockApiService {

    private final RestTemplate restTemplate;

    public MockApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<StudentDTO> fetchStudentsFromMock() {
        String url = "https://41743d1d-d185-4483-b97c-124c16a44d98.mock.pstmn.io/mock/students";
        StudentDTO[] students = restTemplate.getForObject(url, StudentDTO[].class);
        return Arrays.asList(students);
    }

    public StudentDTO fetchStudentById(int id) {
        String url = "https://41743d1d-d185-4483-b97c-124c16a44d98.mock.pstmn.io/mock/students/{id}";
        return restTemplate.getForObject(url, StudentDTO.class);
    }
}