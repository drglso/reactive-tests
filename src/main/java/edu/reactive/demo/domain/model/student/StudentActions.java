package edu.reactive.demo.domain.model.student;

public interface StudentActions {

    default Student update(Student updatedStudent, Integer id) {
        var newStudent = Student.builder();
        newStudent.id(id);
        if (updatedStudent.getDocumentType() != null) {
            newStudent.documentType(updatedStudent.getDocumentType());
        }
        if (updatedStudent.getDocumentNumber() != null) {
            newStudent.documentNumber(updatedStudent.getDocumentNumber());
        }
        if (updatedStudent.getName() != null) {
            newStudent.name(updatedStudent.getName());
        }
        if (updatedStudent.getGuardianID() != null) {
            newStudent.guardianID(updatedStudent.getGuardianID());
        }
        if (updatedStudent.getTeacherID() != null) {
            newStudent.teacherID(updatedStudent.getTeacherID());
        }
        if (updatedStudent.getGroupID() != null) {
            newStudent.groupID(updatedStudent.getGroupID());
        }
        return newStudent.build();
    }
}
