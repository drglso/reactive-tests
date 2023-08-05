package edu.reactive.demo.domain.model.teacher;

public interface TeacherActions {

    default Teacher update(Teacher updatedTeacher, Integer id) {
        var newTeacher = Teacher.builder();
        newTeacher.id(id);
        if (updatedTeacher.getDocumentType() != null) {
            newTeacher.documentType(updatedTeacher.getDocumentType());
        }
        if (updatedTeacher.getDocumentNumber() != null) {
            newTeacher.documentNumber(updatedTeacher.getDocumentNumber());
        }
        if (updatedTeacher.getName() != null) {
            newTeacher.name(updatedTeacher.getName());
        }
        if (updatedTeacher.getAddress() != null) {
            newTeacher.address(updatedTeacher.getAddress());
        }
        if (updatedTeacher.getCellPhone() != null) {
            newTeacher.cellPhone(updatedTeacher.getCellPhone());
        }
        if (updatedTeacher.getEmail() != null) {
            newTeacher.email(updatedTeacher.getEmail());
        }
        if (updatedTeacher.getGroupId() != null) {
            newTeacher.groupId(updatedTeacher.getGroupId());
        }
        return newTeacher.build();
    }
}
