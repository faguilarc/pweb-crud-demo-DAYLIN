package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.CourseDto;

import java.sql.SQLException;
import java.util.List;


public interface CourseService {

    void createCourse(CourseDto courseDto) ;

    void updateCourse(CourseDto courseDto) ;

    List<CourseDto> listCourse() ;

    CourseDto getCourseById(String id);

    void deleteCourse(String  id) ;
}