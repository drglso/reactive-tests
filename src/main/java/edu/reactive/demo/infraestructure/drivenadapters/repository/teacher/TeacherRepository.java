package edu.reactive.demo.infraestructure.drivenadapters.repository.teacher;

import edu.reactive.demo.domain.model.teacher.Teacher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface TeacherRepository extends R2dbcRepository<Teacher, Integer> {

    @Query("SELECT * FROM teacher WHERE document_type = :documentType AND document_number = :documentNumber")
    Mono<Teacher> findTeacherByDocument(String documentType, String documentNumber);
}
