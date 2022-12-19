package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.CourseDto;

import java.sql.SQLException;
import java.util.List;


public interface CourseService {

    void createCourse(CourseDto courseDto) throws SQLException;

    void updateCourse(CourseDto courseDto) throws SQLException;

    List<CourseDto> listCourse() throws SQLException;

    CourseDto getCourseById(String id) throws SQLException;

    void deleteCourse(String  id) throws SQLException;
}