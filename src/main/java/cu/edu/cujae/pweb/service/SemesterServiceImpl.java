package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.SemesterDto;
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
public class SemesterServiceImpl implements SemesterService{

    @Autowired
    private RestService restService;


    @Override
    public void createSemester(SemesterDto semester) {
        restService.POST("/api/v1/semester", semester, String.class).getBody();
    }

    @Override
    public void updateSemester(SemesterDto semester) {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/semester",params, semester, String.class).getBody();

    }

    @Override
    public List<SemesterDto> listSemester() {
        List<SemesterDto> semesterDtoList = new ArrayList<SemesterDto>();

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<SemesterDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String)restService.GET("/api/v1/semester", params, String.class).getBody();
            semesterDtoList = apiRestMapper.mapList(response, SemesterDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return semesterDtoList;

    }

    @Override
    public SemesterDto getSemesterById(String id) {
        SemesterDto semesterDto = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<SemesterDto> apiRestMapper = new ApiRestMapper<>();
            UriTemplate template = new UriTemplate("/api/v1/semester/{id}");
            String uri = template.expand(id).toString();

            String response = (String)restService.GET(uri, params, String.class).getBody();
            semesterDto = apiRestMapper.mapOne(response, SemesterDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return semesterDto;
    }

    @Override
    public void deleteSemester(String id) {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/semester/{id}");
        String uri = template.expand(id).toString();
        restService.DELETE(uri,params,String.class).getBody();
    }
}
