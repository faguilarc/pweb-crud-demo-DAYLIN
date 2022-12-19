package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.GroupsDto;

import java.util.List;

public interface GroupsService {
    List<GroupsDto> getGroups();
    GroupsDto getGroupsById(String id);
    void createGroups(GroupsDto groups);
    void updateGroups(GroupsDto groups);
    void deleteGroups(String id);
}
