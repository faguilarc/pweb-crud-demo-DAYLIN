package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.StudentHistoryDto;
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
public class StudentHistoryServiceImpl implements StudentHistoryService{

    @Autowired
    RestService restService;

    @Override
    public void createStudentHistory(StudentHistoryDto studentHistoryDto) {
        restService.POST("/api/v1/studenthistory", studentHistoryDto, String.class).getBody();
    }

    @Override
    public void updateStudentHistory(StudentHistoryDto studentHistoryDto)  {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/studenthistory",params, studentHistoryDto, String.class).getBody();

    }

    @Override
    public List<StudentHistoryDto> listStudentHistory()  {
        List<StudentHistoryDto> historyDtos = new ArrayList<StudentHistoryDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentHistoryDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String)restService.GET("/api/v1/studenthistory", params, String.class).getBody();
            historyDtos = apiRestMapper.mapList(response, StudentHistoryDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historyDtos;
    }

    @Override
    public StudentHistoryDto getStudentHistoryById(String id) {
        StudentHistoryDto historyDto = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentHistoryDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/studenthistory/{id}");
            String uri = template.expand(id).toString();
            String response = (String)restService.GET(uri, params, String.class).getBody();
            historyDto = apiRestMapper.mapOne(response, StudentHistoryDto.class);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return historyDto;
    }

    @Override
    public void deleteStudentHistory(String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/studenthistory/{id}");
        String uri = template.expand(id).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
    }

    @Override
    public List<StudentHistoryDto> AssignNumberList()  {
        return null;
    }
}
