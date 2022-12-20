package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.StudentHistoryDto;

import java.sql.SQLException;
import java.util.List;

public interface StudentHistoryService {

    void createStudentHistory(StudentHistoryDto studentHistoryDto) throws SQLException;

    void updateStudentHistory(StudentHistoryDto studentHistoryDto) throws SQLException;

    List<StudentHistoryDto> listStudentHistory() throws SQLException;

    StudentHistoryDto getStudentHistoryById(String id) throws SQLException;

    void deleteStudentHistory(String  id) throws SQLException;

    List<StudentHistoryDto> AssignNumberList() throws SQLException;



}
