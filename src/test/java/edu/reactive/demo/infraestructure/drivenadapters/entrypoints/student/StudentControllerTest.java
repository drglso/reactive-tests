package edu.reactive.demo.infraestructure.drivenadapters.entrypoints.student;

import edu.reactive.demo.domain.model.student.Student;
import edu.reactive.demo.domain.usecase.student.StudentUseCase;
import edu.reactive.demo.infraestructure.entrypoints.student.StudentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    private StudentController studentController;

    private StudentUseCase studentUseCase;

    private Student expectedStudent;

    @BeforeEach
    public void setUp() {
        expectedStudent = Student.builder().documentType("CC").documentNumber("123")
                .groupID(123).guardianID(1245).teacherID(1484).build();
        MockitoAnnotations.openMocks(this);
        studentUseCase = mock(StudentUseCase.class);
        studentController = new StudentController(studentUseCase);
    }

    @Test
    void testGetStudentByIdSuccessful() {
        when(studentUseCase.getStudentById(any())).thenReturn(Mono.just(expectedStudent));
        StepVerifier.create(studentController.getStudent(123))
                .expectNext(expectedStudent)
                .verifyComplete();

    }

    @Test
    void testGetStudentByIdFailed() {
        when(studentUseCase.getStudentById(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()));
        StepVerifier.create(studentController.getStudent(123))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testGetStudentByDocumentTypeSuccessful() {
        when(studentUseCase.getStudentByDocument(any())).thenReturn(Mono.just(expectedStudent));
        StepVerifier.create(studentController.getStudentByDocumentType("CC", "123"))
                .expectNext(expectedStudent)
                .verifyComplete();

    }

    @Test
    void testGetStudentByDocumentTypeFailed() {
        when(studentUseCase.getStudentByDocument(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()));
        StepVerifier.create(studentController.getStudentByDocumentType("CC", "123"))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void createStudentSuccessful() {
        when(studentUseCase.saveStudent(any(Student.class))).thenReturn(Mono.just(expectedStudent));
        StepVerifier.create(studentController.saveStudent(expectedStudent))
                .expectNext(expectedStudent)
                .verifyComplete();
    }

    @Test
    void createStudentFailed() {
        when(studentUseCase.saveStudent(any(Student.class))).thenReturn(Mono.error(
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause()));
        StepVerifier.create(studentController.saveStudent(expectedStudent))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteStudentSuccessful() {
        when(studentUseCase.deleteStudent(any())).thenReturn(Mono.empty());
        StepVerifier.create(studentController.deleteStudent(123))
                .expectNext()
                .verifyComplete();
    }

    @Test
    void testDeleteStudentFailed() {
        when(studentUseCase.deleteStudent(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()));
        StepVerifier.create(studentController.deleteStudent(123))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateStudentSuccessful() {
        when(studentUseCase.udpateStudent(any(Student.class), any(Integer.class)))
                .thenReturn(Mono.just(expectedStudent));
        StepVerifier.create(studentController.updateStudent(expectedStudent,123))
                .expectNext(expectedStudent)
                .verifyComplete();
    }

    @Test
    void testUpdateStudentFailed() {
        when(studentUseCase.udpateStudent(any(Student.class), any(Integer.class)))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")));

        StepVerifier.create(studentController.updateStudent(expectedStudent,123))
                .expectError(ResponseStatusException.class)
                .verify();
    }

}
