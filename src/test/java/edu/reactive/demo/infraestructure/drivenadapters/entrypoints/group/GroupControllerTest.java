package edu.reactive.demo.infraestructure.drivenadapters.entrypoints.group;

import edu.reactive.demo.domain.model.group.Group;
import edu.reactive.demo.domain.usecase.group.GroupUseCase;
import edu.reactive.demo.infraestructure.entrypoints.group.GroupController;
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
class GroupControllerTest {

    private GroupController groupController;

    private GroupUseCase groupUseCase;

    private Group expectedGroup;

    @BeforeEach
    void setUp() {
        expectedGroup = Group.builder().classRoom(123).grade("5B").classRoom(204).build();
        MockitoAnnotations.openMocks(this);
        groupUseCase = mock(GroupUseCase.class);
        groupController = new GroupController(groupUseCase);
    }

    @Test
    void testGetGroupByIdSuccessful() {
        when(groupUseCase.getGroupById(any())).thenReturn(Mono.just(expectedGroup));
        StepVerifier.create(groupController.getGroup(123))
                .expectNext(expectedGroup)
                .verifyComplete();

    }

    @Test
    void testGetGroupByIdFailed() {
        when(groupUseCase.getGroupById(any())).thenReturn(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Group not found").getMostSpecificCause()));
        StepVerifier.create(groupController.getGroup(123))
                .expectError(ResponseStatusException.class)
                .verify();
    }
}
