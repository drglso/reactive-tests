package edu.reactive.demo.domain.usecase.teacher;

import edu.reactive.demo.domain.model.identification.Identification;
import edu.reactive.demo.domain.model.teacher.Teacher;
import edu.reactive.demo.infraestructure.drivenadapters.repository.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TeacherUseCase {

    private final TeacherRepository teacherRepository;

    public Mono<Teacher> getTeacherById(Integer id) {
        return teacherRepository.findById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Teacher not found").getMostSpecificCause()));
    }

    public Mono<Teacher> saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }

    public Mono<Teacher> getTeacherByDocument(Identification identification) {
        return teacherRepository.findTeacherByDocument(identification.getDocumentType(),
                        identification.getDocumentNumber())
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Teacher not found").getMostSpecificCause()));
    }


    public Mono<Void> deleteTeacher(Integer id) {
        return teacherRepository.deleteById(id)
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }

    public Flux<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Mono<Teacher> udpateTeacher(Teacher teacherToUpdate, Integer id) {
        return teacherRepository.findById(id)
                .flatMap(existingTeacher ->
                        teacherRepository.save(existingTeacher.update(teacherToUpdate, id)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Teacher not found").getMostSpecificCause()))
                .doOnError(throwable -> {
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).getMostSpecificCause();
                });
    }
}
