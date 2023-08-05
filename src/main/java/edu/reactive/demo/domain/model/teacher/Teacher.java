package edu.reactive.demo.domain.model.teacher;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Teacher")
@Builder
public class Teacher implements TeacherActions {
    @Id
    private Integer id;
    private String documentType;
    private String documentNumber;
    private String name;
    private String address;
    private String cellPhone;
    private String email;
    @Column("group_id")
    private Integer groupId;
}
