package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.GroupsDto;
import cu.edu.cujae.pweb.dto.YearDto;
import cu.edu.cujae.pweb.service.GroupsService;
import cu.edu.cujae.pweb.service.YearService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;
import java.util.UUID;

@Component
@ManagedBean
@ViewScoped
public class ManageGroupsBean {

    private GroupsDto selectedGroups;
    private List<GroupsDto> groupsDtoList;
    private String selectedYear;

    private List<YearDto> yearDtoList;

    @Autowired
    private YearService yearService;
    @Autowired
    private GroupsService groupsService;

    public ManageGroupsBean() {
    }

    public void openNew() {
        this.selectedGroups = new GroupsDto();

    }


    public void saveGroups() {
        if (this.selectedGroups.getId_group() == null) {
            this.selectedGroups.setId_group(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));

            this.selectedGroups.setId_year(this.selectedYear);

            groupsService.createGroups(this.selectedGroups);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_groups_added");
            System.out.println("Ya llame al servicio de crear");
        } else {
            groupsService.updateGroups(this.selectedGroups);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_groups_edited");
        }
        PrimeFaces.current().executeScript("PF('manageGroupsDialog').hide()");
        refresh();
        PrimeFaces.current().ajax().update("form:dt-groups");
    }

    public void deleteGroups(){
        try {
            groupsService.deleteGroups(this.selectedGroups.getId_group());
            this.selectedGroups = new GroupsDto();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_groups_deleted");
            PrimeFaces.current().ajax().update("form:dt-groups");
        }catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }


    public GroupsDto getSelectedGroups() {
        return selectedGroups;
    }

    public void setSelectedGroups(GroupsDto selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    public List<GroupsDto> getGroupsDtoList() {
        return groupsService.getGroups();
    }
    public void refresh(){
        groupsDtoList= groupsService.getGroups();
    }
    public void setGroupsDtoList(List<GroupsDto> groupsDtoList) {
        this.groupsDtoList = groupsDtoList;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public List<YearDto> getYearDtoList() {
        return yearService.getYears();
    }

    public void setYearDtoList(List<YearDto> yearDtoList) {
        this.yearDtoList = yearDtoList;
    }

    public YearService getYearService() {
        return yearService;
    }

    public void setYearService(YearService yearService) {
        this.yearService = yearService;
    }

    public GroupsService getGroupsService() {
        return groupsService;
    }

    public void setGroupsService(GroupsService groupsService) {
        this.groupsService = groupsService;
    }
}
