package edu.reactive.demo.infraestructure.entrypoints.student;

import edu.reactive.demo.domain.model.identification.Identification;
import edu.reactive.demo.domain.model.student.Student;
import edu.reactive.demo.domain.usecase.student.StudentUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentUseCase studentUseCase;

    private final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/{id}")
    public Mono<Student> getStudent(@PathVariable Integer id) {
        return studentUseCase.getStudentById(id);

    }

    @GetMapping("/{documentType}/{documentNumber}")
    public Mono<Student> getStudentByDocumentType(@PathVariable String documentType,
                                                  @PathVariable String documentNumber) {

        return studentUseCase.getStudentByDocument(Identification.builder()
                .documentType(documentType)
                .documentNumber(documentNumber).build());
    }

    @PostMapping("")
    public Mono<Student> saveStudent(@RequestBody Student student) {
        return studentUseCase.saveStudent(student);

    }

    @PutMapping("/update/{id}")
    public Mono<Student> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
        return studentUseCase.udpateStudent(student, id);

    }

    @DeleteMapping("")
    public Mono<Void> deleteStudent(@PathVariable Integer id) {
        return studentUseCase.deleteStudent(id);
    }

    @GetMapping("/all")
    public Flux<Student> getAllStudents() {
        return studentUseCase.findAllStudents();
    }


}
