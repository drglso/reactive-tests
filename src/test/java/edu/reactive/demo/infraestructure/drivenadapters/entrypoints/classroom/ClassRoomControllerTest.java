package edu.reactive.demo.infraestructure.drivenadapters.entrypoints.classroom;

import edu.reactive.demo.domain.model.classroom.ClassRoom;
import edu.reactive.demo.domain.usecase.classroom.ClassRoomUseCase;
import edu.reactive.demo.infraestructure.entrypoints.classroom.ClassRoomController;
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
class ClassRoomControllerTest {

    private ClassRoomController classRoomController;

    private ClassRoomUseCase classRoomUseCase;

    private ClassRoom expectedClassRoom;

    @BeforeEach
    void setUp() {
        expectedClassRoom = ClassRoom.builder().name("5B").location("Principal site").build();
        MockitoAnnotations.openMocks(this);
        classRoomUseCase = mock(ClassRoomUseCase.class);
        classRoomController = new ClassRoomController(classRoomUseCase);
    }

    @Test
    void testGetGroupByIdSuccessful() {
        when(classRoomUseCase.getClassRoomById(any())).thenReturn(Mono.just(expectedClassRoom));
        StepVerifier.create(classRoomController.getClassRoom(123))
                .expectNext(expectedClassRoom)
                .verifyComplete();

    }

    @Test
    void testGetGroupByIdFailed() {
        when(classRoomUseCase.getClassRoomById(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Group not found").getMostSpecificCause()));
        StepVerifier.create(classRoomController.getClassRoom(123))
                .expectError(ResponseStatusException.class)
                .verify();
    }
}
