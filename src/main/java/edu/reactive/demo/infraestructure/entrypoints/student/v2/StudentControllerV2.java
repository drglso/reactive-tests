package edu.reactive.demo.infraestructure.entrypoints.student.v2;

import edu.reactive.demo.domain.model.student.Student;
import edu.reactive.demo.domain.usecase.student.StudentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/studentV2")
public class StudentControllerV2 {

    private final StudentUseCase studentUseCase;

    @GetMapping("/all/{groupId}")
    public Flux<Student> getAllStudents(@PathVariable Integer groupId) {
        return studentUseCase.findAllStudentsByGroup(groupId);
    }

}
