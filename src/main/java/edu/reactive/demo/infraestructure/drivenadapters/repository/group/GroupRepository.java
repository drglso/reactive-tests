package edu.reactive.demo.infraestructure.drivenadapters.repository.group;

import edu.reactive.demo.domain.model.group.Group;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface GroupRepository extends R2dbcRepository<Group, Integer> {
}
