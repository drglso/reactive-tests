package edu.reactive.demo.domain.usecase.classroom;

import edu.reactive.demo.domain.model.classroom.ClassRoom;
import edu.reactive.demo.infraestructure.drivenadapters.repository.classroom.ClassRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClassRoomUseCase {

    private final ClassRoomRepository classRoomRepository;

    public Mono<ClassRoom> getClassRoomById(Integer id) {
        return classRoomRepository.findById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "ClassRoom not found").getMostSpecificCause()));
    }

    public Flux<ClassRoom> findAllClassRoom() {
        return classRoomRepository.findAll();
    }
}
