package edu.reactive.demo.infraestructure.entrypoints.teacher;

import edu.reactive.demo.domain.model.identification.Identification;
import edu.reactive.demo.domain.model.teacher.Teacher;
import edu.reactive.demo.domain.usecase.teacher.TeacherUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherUseCase teacherUseCase;

    @GetMapping("/{id}")
    public Mono<Teacher> getTeacher(@PathVariable Integer id) {
        return teacherUseCase.getTeacherById(id);
    }

    @PostMapping("/")
    public Mono<Teacher> saveTeacher(@RequestBody Teacher teacher) {
        return teacherUseCase.saveTeacher(teacher);
    }

    @GetMapping("/{documentType}/{documentNumber}")
    public Mono<Teacher> getTeacherByDocumentType(@PathVariable String documentType,
                                                  @PathVariable String documentNumber) {
        return teacherUseCase.getTeacherByDocument(
                Identification.builder()
                        .documentType(documentType)
                        .documentNumber(documentNumber)
                        .build());
    }

    @DeleteMapping("")
    public Mono<Void> deleteTeacher(@PathVariable Integer id) {
        return teacherUseCase.deleteTeacher(id);
    }

    @GetMapping("/all")
    public Flux<Teacher> getAllTeachers() {
        return teacherUseCase.findAllTeachers();
    }

    @PutMapping("/update/{id}")
    public Mono<Teacher> updateTeacher(@RequestBody Teacher teacher, @PathVariable Integer id) {
        return teacherUseCase.udpateTeacher(teacher, id);

    }

}
