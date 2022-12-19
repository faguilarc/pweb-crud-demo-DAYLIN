package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getStudent();
    StudentDto getStudentById(String id);
    void createStudent(StudentDto student);
    void updateStudent(StudentDto student);
    void deleteStudent(String id);
}
