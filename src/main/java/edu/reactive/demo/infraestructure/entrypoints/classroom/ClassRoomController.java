package edu.reactive.demo.infraestructure.entrypoints.classroom;

import edu.reactive.demo.domain.model.classroom.ClassRoom;
import edu.reactive.demo.domain.usecase.classroom.ClassRoomUseCase;
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
@RequestMapping("/classRoom")
public class ClassRoomController {

    private final ClassRoomUseCase classRoomUseCase;

    @GetMapping("/{id}")
    public Mono<ClassRoom> getClassRoom(@PathVariable Integer id) {
        return classRoomUseCase.getClassRoomById(id);
    }

    @GetMapping("/all")
    public Flux<ClassRoom> getAllClassRoom() {
        return classRoomUseCase.findAllClassRoom();
    }

}
