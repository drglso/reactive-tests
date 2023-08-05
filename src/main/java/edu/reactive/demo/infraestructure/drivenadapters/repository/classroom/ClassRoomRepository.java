package edu.reactive.demo.infraestructure.drivenadapters.repository.classroom;

import edu.reactive.demo.domain.model.classroom.ClassRoom;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ClassRoomRepository extends R2dbcRepository<ClassRoom, Integer> {
}
