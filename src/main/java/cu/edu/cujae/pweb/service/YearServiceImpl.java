package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.YearDto;
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
public class YearServiceImpl implements YearService {


    @Autowired
    private RestService restService;

    @Override
    public List<YearDto> getYears() {
        List<YearDto> yearDtoList = new ArrayList<YearDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<YearDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String) restService.GET("/api/v1/year", params, String.class).getBody();
            yearDtoList = apiRestMapper.mapList(response, YearDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return yearDtoList;
    }

    @Override
    public YearDto getYearById(String id) {
        YearDto yearDto = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<YearDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/year/{id}");
            String uri = template.expand(id).toString();
            String response = (String)restService.GET(uri, params, String.class).getBody();
            yearDto = apiRestMapper.mapOne(response, YearDto.class);
        }catch (Exception e){
            // TODO: handle exception
        }
        return yearDto;
    }

    @Override
    public void createYear(YearDto year) {
         restService.POST("/api/v1/year", year, String.class).getBody();

    }

    @Override
    public void updateYear(YearDto year) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/year", params, year, String.class).getBody();
    }

    @Override
    public void deleteYear(String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/year/{id}");
        String uri = template.expand(id).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
    }
}
