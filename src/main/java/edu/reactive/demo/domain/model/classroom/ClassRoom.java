package edu.reactive.demo.domain.model.classroom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@Table("Classroom")
public class ClassRoom {
    @Id
    private Integer id;
    private String location;
    private String name;

}
