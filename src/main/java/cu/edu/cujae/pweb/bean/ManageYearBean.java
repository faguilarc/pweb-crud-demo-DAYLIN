package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.YearDto;
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
public class ManageYearBean {
    private YearDto yearDto;
    private YearDto selectedYear;
    private List<YearDto> years;

    @Autowired
    private YearService yearService;

    public ManageYearBean(){ }



    //Se ejecuta al dar clic en el button Nuevo
    public void openNew(){
        this.selectedYear = new YearDto();

    }

    public void OpenForEdit(){}

    //Se ejecuta al dar clic en el button dentro del dialog para salvar o registrar al curso
    public void saveYear(){
        System.out.println("Estoy insertando añoooooo");
        if (this.selectedYear.getId_year() == null){

            this.selectedYear.setId_year(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            yearService.createYear(this.selectedYear);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_year_added");
            System.out.println("Ya llame al servicio de crear");
        }else{
            yearService.updateYear(this.selectedYear);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_year_edited");

        }
        refresh();
        PrimeFaces.current().executeScript("PF('manageYearDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-years");

    }

    //Permite eliminar un year
    public void deleteYear(){
        try {
            yearService.deleteYear(this.selectedYear.getId_year());
            this.selectedYear = new YearDto();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_year_deleted");
            //refresh();
            PrimeFaces.current().ajax().update("form:dt-years");
            System.out.println("Estoy eliminando añoooooo");
        }catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public YearDto getYearDto(){return yearDto;}
    public  void setYearDto(YearDto yearDto){this.yearDto = yearDto;}
    public YearDto getSelectedYear(){return selectedYear;}
    public void setSelectedYear(YearDto selectedYear){this.selectedYear = selectedYear;}
    public void refresh(){years = yearService.getYears();}
    public List<YearDto> getYears() {
        years = yearService.getYears();

        return years;
    }


    public void setYears(List<YearDto> years){this.years = years;}
}
