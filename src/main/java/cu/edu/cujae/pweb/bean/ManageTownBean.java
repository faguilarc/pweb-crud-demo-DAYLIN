package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.TownDto;
import cu.edu.cujae.pweb.service.TownService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@ManagedBean
@ViewScoped
public class ManageTownBean {
    private TownDto townDto;
    private TownDto selectedTown;
    private List<TownDto> towns;

    @Autowired
    private TownService townService;

    public ManageTownBean() {
    }

    public void openNew() {

        this.selectedTown = new TownDto();
    }

    public void openForEdit() {
    }

    public void saveTown() {
        System.out.println("Estoy insertando Municipio");
        if (this.selectedTown.getId_town() == null) {

            this.selectedTown.setId_town(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            townService.createTown(this.selectedTown);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_town_added");
            System.out.println("Ya llame al servicio de crear");
        } else {
            townService.updateTown(this.selectedTown);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_town_edited");

        }
        PrimeFaces.current().executeScript("PF('manageTownDialog').hide()");
        refresh();
        PrimeFaces.current().ajax().update("form:dt-towns");
    }

    public void deleteTown() {
        try {
            townService.deleteTown(this.selectedTown.getId_town());
            this.selectedTown = new TownDto();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_town_deleted");
            PrimeFaces.current().ajax().update("form:dt-towns");
            System.out.println("Estoy eliminando provincia");
        } catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public TownDto getTownDto() { return townDto;}
    public void setTownDto(TownDto townDto) {this.townDto = townDto; }
    public TownDto getSelectedTown() {return selectedTown; }
    public void setSelectedTown(TownDto selectedTown) { this.selectedTown = selectedTown; }
    public List<TownDto> getTowns() {
        towns = townService.getTowns();
        return towns;
    }
    public void refresh() {
        towns = townService.getTowns();
    }
    public void setTowns(List<TownDto> towns) {
        this.towns = towns;
    }
}
