package edu.reactive.demo.domain.usecase.group;

import edu.reactive.demo.domain.model.group.Group;
import edu.reactive.demo.infraestructure.drivenadapters.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GroupUseCase {

    private final GroupRepository groupRepository;

    public Mono<Group> getGroupById(Integer id) {
        return groupRepository.findById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Group not found").getMostSpecificCause()));
    }

    public Flux<Group> findAllGroups() {
        return groupRepository.findAll();
    }
}
