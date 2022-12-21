package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.StudentHistoryDto;

import java.sql.SQLException;
import java.util.List;

public interface StudentHistoryService {

    void createStudentHistory(StudentHistoryDto studentHistoryDto) ;

    void updateStudentHistory(StudentHistoryDto studentHistoryDto) ;

    List<StudentHistoryDto> listStudentHistory() ;

    StudentHistoryDto getStudentHistoryById(String id) ;

    void deleteStudentHistory(String  id) ;

    List<StudentHistoryDto> AssignNumberList() ;



}
