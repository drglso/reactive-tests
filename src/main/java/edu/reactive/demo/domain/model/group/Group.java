package edu.reactive.demo.domain.model.group;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Groups")
@Builder
public class Group {
    @Id
    private Integer id;
    private String grade;
    private Integer classRoom;
}
