package edu.reactive.demo.infraestructure.drivenadapters.repository.student;

import edu.reactive.demo.domain.model.student.Student;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface StudentRepository extends R2dbcRepository<Student, Integer> {

    @Query("SELECT * FROM student WHERE document_type = :documentType AND document_number = :documentNumber")
    Mono<Student> findStudentByDocument(String documentType, String documentNumber);

}
