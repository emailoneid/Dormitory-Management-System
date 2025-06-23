package cz.mendelu.service;

import cz.mendelu.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MockApiService {

    private static final Logger log = LoggerFactory.getLogger(MockApiService.class);

    private final RestTemplate restTemplate;

    private static final String STUDENT_API_BASE_URL = "https://41743d1d-d185-4483-b97c-124c16a44d98.mock.pstmn.io/mock/students";

    public MockApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches a StudentDTO from the external mock API using the studentId.
     */
    public Optional<StudentDTO> getStudentByStudentId(String studentId) {
        log.info("Fetching students from Mock API: {}", STUDENT_API_BASE_URL);

        try {
            StudentDTO[] studentDtosArray = restTemplate.getForObject(STUDENT_API_BASE_URL, StudentDTO[].class);

            if (studentDtosArray != null) {
                List<StudentDTO> studentDtos = Arrays.asList(studentDtosArray);
                return studentDtos.stream()
                        .filter(dto -> studentId.equals(dto.getStudentId()))
                        .findFirst();
            } else {
                log.warn("Mock API returned null array from: {}", STUDENT_API_BASE_URL);
            }
        } catch (HttpClientErrorException.NotFound e) {
            log.error("404 Not Found from Mock API: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error fetching students from Mock API: {}", e.getMessage(), e);
        }

        return Optional.empty();
    }
}
