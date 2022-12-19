package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.GroupsDto;
import cu.edu.cujae.pweb.dto.StudentDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private RestService restService;

    @Override
    public List<StudentDto> getStudent() {
        List<StudentDto> studentDtoList = new ArrayList<StudentDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String) restService.GET("/api/v1/student", params, String.class).getBody();
            studentDtoList = apiRestMapper.mapList(response, StudentDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentDtoList;
    }

    @Override
    public StudentDto getStudentById(String id) {
        StudentDto studentDto = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/student/{id}");
            String uri = template.expand(id).toString();
            String response = (String)restService.GET(uri, params, String.class).getBody();
            studentDto = apiRestMapper.mapOne(response, StudentDto.class);
        }catch (Exception e){
            // TODO: handle exception
        }
        return studentDto;
    }

    @Override
    public void createStudent(StudentDto student) {
        restService.POST("/api/v1/student", student, String.class).getBody();
    }

    @Override
    public void updateStudent(StudentDto student) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/student", params, student, String.class).getBody();
    }

    @Override
    public void deleteStudent(String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/student/{id}");
        String uri = template.expand(id).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
    }
}
