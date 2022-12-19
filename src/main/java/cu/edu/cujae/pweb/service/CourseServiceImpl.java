package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.CourseDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private RestService restService;

    @Override
    public List<CourseDto> listCourse() throws SQLException {
        List<CourseDto> courseList = new ArrayList<CourseDto>();

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<CourseDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String)restService.GET("/api/v1/courses", params, String.class).getBody();
            courseList = apiRestMapper.mapList(response, CourseDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseList;

    }

    @Override
    public void createCourse(CourseDto courseDto) throws SQLException {
        restService.POST("/api/v1/courses", courseDto, String.class).getBody();
    }

    @Override
    public void updateCourse(CourseDto courseDto) throws SQLException {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/courses",params, courseDto, String.class).getBody();
    }

    @Override
    public void deleteCourse(String id) throws SQLException {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/courses/{id}");
        String uri = template.expand(id).toString();
        restService.DELETE(uri,params,String.class).getBody();
    }

    @Override
    public CourseDto getCourseById(String id) throws SQLException {
        CourseDto courseDto = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<CourseDto> apiRestMapper = new ApiRestMapper<>();
            UriTemplate template = new UriTemplate("/api/v1/courses/{id}");
            String uri = template.expand(id).toString();

            String response = (String)restService.GET(uri, params, String.class).getBody();
            courseDto = apiRestMapper.mapOne(response, CourseDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return courseDto;
    }

}
