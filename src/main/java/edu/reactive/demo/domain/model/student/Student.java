package edu.reactive.demo.domain.model.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Student")
@Builder
public class Student implements StudentActions {
    @Id
    private Integer id;
    private String documentType;
    private String documentNumber;
    private String name;
    @Column("guardian_id")
    private Integer guardianID;
    @Column("teacher_id")
    private Integer teacherID;
    @Column("group_id")
    private Integer groupID;
}
