package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.StudentDto;
import cu.edu.cujae.pweb.dto.SubjectDto;
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
public class SubjectServiceImpl implements SubjectService{
    @Autowired
    private RestService restService;

    @Override
    public List<SubjectDto> getSubject() {
        List<SubjectDto> subjectDtoList = new ArrayList<SubjectDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<SubjectDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String) restService.GET("/api/v1/subject", params, String.class).getBody();
            subjectDtoList = apiRestMapper.mapList(response, SubjectDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subjectDtoList;
    }

    @Override
    public SubjectDto getSubjectById(String id) {
        SubjectDto subjectDto = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<SubjectDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/subject/{id}");
            String uri = template.expand(id).toString();
            String response = (String)restService.GET(uri, params, String.class).getBody();
            subjectDto = apiRestMapper.mapOne(response, SubjectDto.class);
        }catch (Exception e){
            // TODO: handle exception
        }
        return subjectDto;
    }

    @Override
    public void createSubject(SubjectDto subject) {
        restService.POST("/api/v1/subject", subject, String.class).getBody();

    }

    @Override
    public void updateSubject(SubjectDto subject) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/subject", params, subject, String.class).getBody();

    }

    @Override
    public void deleteSubject(String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/subject/{id}");
        String uri = template.expand(id).toString();
        restService.DELETE(uri, params, String.class, null).getBody();

    }
}
