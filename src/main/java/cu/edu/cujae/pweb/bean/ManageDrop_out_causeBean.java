package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.Drop_out_causeDto;
import cu.edu.cujae.pweb.service.Drop_out_causeService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;
import java.util.UUID;

@Component
@ManagedBean
@ViewScoped
public class ManageDrop_out_causeBean {
    private Drop_out_causeDto drop_out_causeDto;
    private Drop_out_causeDto selectedDrop_out_cause;
    private List<Drop_out_causeDto> drop_out_causes;

    @Autowired
    private Drop_out_causeService drop_out_causeService;

    public ManageDrop_out_causeBean(){}



    public void openNew(){
        this.selectedDrop_out_cause = new Drop_out_causeDto();}

    public void openForEdit(){

    }

    public void saveDrop_out_cause(){
        if (this.selectedDrop_out_cause.getId_drop_out_cause() == null){
            this.selectedDrop_out_cause.setId_drop_out_cause(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            drop_out_causeService.createDrop_out_cause(this.selectedDrop_out_cause);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_drop_out_cause_added");
        }else{
            drop_out_causeService.updateDrop_out_cause(this.selectedDrop_out_cause);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_drop_out_cause_edited");
        }
        PrimeFaces.current().executeScript("PF('manageDrop_out_causeDialog').hide()");
        refresh();
        PrimeFaces.current().ajax().update("form:dt-drop_out_causes");
    }

    public void deleteDrop_out_cause(){
        try {
            drop_out_causeService.deleteDrop_out_cause(this.selectedDrop_out_cause.getId_drop_out_cause());
            this.selectedDrop_out_cause = new Drop_out_causeDto();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_drop_out_cause_deleted");
            PrimeFaces.current().ajax().update("form:dt-drop_out_causes");
        }catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public Drop_out_causeDto getDrop_out_causeDto() {
        return drop_out_causeDto;
    }
    public void setDrop_out_causeDto(Drop_out_causeDto drop_out_causeDto) {
        this.drop_out_causeDto = drop_out_causeDto;
    }
    public Drop_out_causeDto getSelectedDrop_out_cause() {
        return selectedDrop_out_cause;
    }
    public void setSelectedDrop_out_cause(Drop_out_causeDto selectedDrop_out_cause) {
        this.selectedDrop_out_cause = selectedDrop_out_cause;
    }
    public void refresh(){drop_out_causes = drop_out_causeService.getDrop_out_cause();}
    public List<Drop_out_causeDto> getDrop_out_causes() {
        drop_out_causes = drop_out_causeService.getDrop_out_cause();
        return drop_out_causes;
    }

    public void setDrop_out_causes(List<Drop_out_causeDto> drop_out_causes) {
        this.drop_out_causes = drop_out_causes;
    }
}
