package edu.reactive.demo.domain.model.student;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Student")
@Builder
@NoArgsConstructor
public class Student implements StudentActions {

    public Student(Integer id, String documentType, String documentNumber, String name, Integer guardianID, Integer teacherID, Integer groupID) {
        this.id = id;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.name = name;
        this.guardianID = guardianID;
        this.teacherID = teacherID;
        this.groupID = groupID;
    }

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
