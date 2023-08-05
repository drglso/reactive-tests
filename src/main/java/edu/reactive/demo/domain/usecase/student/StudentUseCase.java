package edu.reactive.demo.domain.usecase.student;

import edu.reactive.demo.domain.model.identification.Identification;
import edu.reactive.demo.domain.model.student.Student;
import edu.reactive.demo.infraestructure.drivenadapters.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StudentUseCase {
    private final StudentRepository studentRepository;

    public Mono<Student> getStudentById(Integer id) {
        return studentRepository.findById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()));
    }

    public Mono<Student> saveStudent(Student student) {
        return studentRepository.save(student)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }

    public Mono<Student> getStudentByDocument(Identification identification) {
        return studentRepository.findStudentByDocument(identification.getDocumentType(),
                        identification.getDocumentNumber())
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()));
    }

    public Mono<Void> deleteStudent(Integer id) {
        return studentRepository.deleteById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }

    public Flux<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Mono<Student> udpateStudent(Student studentToUpdate, Integer id) {
        return studentRepository.findById(id)
                .flatMap(existingStudent ->
                        studentRepository.save(existingStudent.update(studentToUpdate, id)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Student not found").getMostSpecificCause()))
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }
}
