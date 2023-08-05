package edu.reactive.demo.infraestructure.drivenadapters.entrypoints.teacher;

import edu.reactive.demo.domain.model.teacher.Teacher;
import edu.reactive.demo.domain.usecase.teacher.TeacherUseCase;
import edu.reactive.demo.infraestructure.entrypoints.teacher.TeacherController;
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
class TeacherControllerTest {

    private TeacherController teacherController;

    private TeacherUseCase teacherUseCase;

    private Teacher expectedTeacher;

    @BeforeEach
    public void setUp() {
        expectedTeacher = Teacher.builder().documentType("CC").documentNumber("123")
                .name("TestName").cellPhone("123").address("testAdress").email("testEmail").groupId(123).build();
        MockitoAnnotations.openMocks(this);
        teacherUseCase = mock(TeacherUseCase.class);
        teacherController = new TeacherController(teacherUseCase);
    }

    @Test
    void testGetTeacherByIdSuccessful() {
        when(teacherUseCase.getTeacherById(any())).thenReturn(Mono.just(expectedTeacher));
        StepVerifier.create(teacherController.getTeacher(123))
                .expectNext(expectedTeacher)
                .verifyComplete();

    }

    @Test
    void testGetTeacherByIdFailed() {
        when(teacherUseCase.getTeacherById(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "teacher not found").getMostSpecificCause()));
        StepVerifier.create(teacherController.getTeacher(123))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testGetTeacherByDocumentTypeSuccessful() {
        when(teacherUseCase.getTeacherByDocument(any())).thenReturn(Mono.just(expectedTeacher));
        StepVerifier.create(teacherController.getTeacherByDocumentType("CC", "123"))
                .expectNext(expectedTeacher)
                .verifyComplete();

    }

    @Test
    void testGetTeacherByDocumentTypeFailed() {
        when(teacherUseCase.getTeacherByDocument(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "teacher not found").getMostSpecificCause()));
        StepVerifier.create(teacherController.getTeacherByDocumentType("CC", "123"))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void createTeacherSuccessful() {
        when(teacherUseCase.saveTeacher(any(Teacher.class))).thenReturn(Mono.just(expectedTeacher));
        StepVerifier.create(teacherController.saveTeacher(expectedTeacher))
                .expectNext(expectedTeacher)
                .verifyComplete();
    }

    @Test
    void createTeacherFailed() {
        when(teacherUseCase.saveTeacher(any(Teacher.class))).thenReturn(Mono.error(
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause()));
        StepVerifier.create(teacherController.saveTeacher(expectedTeacher))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteTeacherSuccessful() {
        when(teacherUseCase.deleteTeacher(any())).thenReturn(Mono.empty());
        StepVerifier.create(teacherController.deleteTeacher(123))
                .expectNext()
                .verifyComplete();
    }

    @Test
    void testDeleteTeacherFailed() {
        when(teacherUseCase.deleteTeacher(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "teacher not found").getMostSpecificCause()));
        StepVerifier.create(teacherController.deleteTeacher(123))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateTeacherSuccessful() {
        when(teacherUseCase.udpateTeacher(any(Teacher.class), any(Integer.class)))
                .thenReturn(Mono.just(expectedTeacher));
        StepVerifier.create(teacherController.updateTeacher(expectedTeacher,123))
                .expectNext(expectedTeacher)
                .verifyComplete();
    }

    @Test
    void testUpdateStudentFailed() {
        when(teacherUseCase.udpateTeacher(any(Teacher.class), any(Integer.class)))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")));

        StepVerifier.create(teacherController.updateTeacher(expectedTeacher,123))
                .expectError(ResponseStatusException.class)
                .verify();
    }

}
