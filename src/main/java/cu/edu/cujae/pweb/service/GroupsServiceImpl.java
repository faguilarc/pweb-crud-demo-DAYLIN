package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.GroupsDto;
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
public class GroupsServiceImpl implements GroupsService{

    @Autowired
    private RestService restService;

    @Override
    public List<GroupsDto> getGroups() {
        List<GroupsDto> groupsList = new ArrayList<GroupsDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<GroupsDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String) restService.GET("/api/v1/groups", params, String.class).getBody();
            groupsList = apiRestMapper.mapList(response, GroupsDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupsList;
    }

    @Override
    public GroupsDto getGroupsById(String id) {
        GroupsDto groupsDto = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<GroupsDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/groups/{id}");
            String uri = template.expand(id).toString();
            String response = (String)restService.GET(uri, params, String.class).getBody();
            groupsDto = apiRestMapper.mapOne(response, GroupsDto.class);
        }catch (Exception e){
            // TODO: handle exception
        }
        return groupsDto;
    }

    @Override
    public void createGroups(GroupsDto groups) {
        restService.POST("/api/v1/groups", groups, String.class).getBody();
    }

    @Override
    public void updateGroups(GroupsDto groups) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/groups", params, groups, String.class).getBody();
    }

    @Override
    public void deleteGroups(String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/groups/{id}");
        String uri = template.expand(id).toString();
        restService.DELETE(uri, params, String.class, null).getBody();

    }
}
