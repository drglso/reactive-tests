package edu.reactive.demo.infraestructure.entrypoints.group;

import edu.reactive.demo.domain.model.group.Group;
import edu.reactive.demo.domain.usecase.group.GroupUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupUseCase groupUseCase;

    @GetMapping("/{id}")
    public Mono<Group> getGroup(@PathVariable Integer id) {
        return groupUseCase.getGroupById(id);
    }

    @GetMapping("/all")
    public Flux<Group> getAllGroups() {
        return groupUseCase.findAllGroups();
    }

}
