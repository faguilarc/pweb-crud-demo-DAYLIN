package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.CourseDto;
import cu.edu.cujae.pweb.dto.SemesterDto;
import cu.edu.cujae.pweb.dto.YearDto;
import cu.edu.cujae.pweb.service.CourseService;
import cu.edu.cujae.pweb.service.SemesterService;
import cu.edu.cujae.pweb.service.YearService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component //Le indica a spring es un componete registrado
@ManagedBean
@ViewScoped
public class ManageSemesterBean {

    private SemesterDto selectedSemester;
    private List<SemesterDto> semesterDtoList;
    private String selectedYear;
    private String selectedCourse;

    private List<YearDto> yearDtoList;
    private List<CourseDto> courseDtoList;

    @Autowired
    private SemesterService semesterService;
    @Autowired
    private YearService yearService;
    @Autowired
    private CourseService courseService;

    public ManageSemesterBean() {
        this.semesterDtoList = new ArrayList<SemesterDto>();
        this.yearDtoList = new ArrayList<YearDto>();
        this.courseDtoList = new ArrayList<CourseDto>();
    }

    public void openNew() {
        this.selectedSemester = new SemesterDto();
    }

    public void saveSemester() throws SQLException {
        if (this.selectedSemester.getId_semester() == null) {
            this.selectedSemester.setId_semester(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.selectedSemester.setId_course(this.selectedCourse);
            this.selectedSemester.setId_year(this.selectedYear);

            semesterService.createSemester(this.selectedSemester);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_semester_added"); //Este code permite mostrar un mensaje exitoso (FacesMessage.SEVERITY_INFO) obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
        } else {
            semesterService.updateSemester(this.selectedSemester);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_semester_edited");
        }
        PrimeFaces.current().executeScript("PF('manageSemesterDialog').hide()");
        refresh();
        PrimeFaces.current().ajax().update("form:dt-semester");
    }

    public void deleteSemester() {
        try {
            semesterService.deleteSemester(this.selectedSemester.getId_semester());
            this.selectedSemester = new SemesterDto();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_semester_deleted");
            PrimeFaces.current().ajax().update("form:dt-semester");
        } catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public void refresh() throws SQLException {
        semesterDtoList = semesterService.listSemester();
    }

    public SemesterDto getSelectedSemester() {
        return selectedSemester;
    }

    public void setSelectedSemester(SemesterDto selectedSemester) {
        this.selectedSemester = selectedSemester;
    }

    public List<SemesterDto> getSemesterDtoList() {
        this.semesterDtoList = semesterService.listSemester();
        return semesterDtoList;
    }

    public void setSemesterDtoList(List<SemesterDto> semesterDtoList) {
        this.semesterDtoList = semesterDtoList;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<YearDto> getYearDtoList() {
        this.yearDtoList = yearService.getYears();
        return yearDtoList;
    }

    public void setYearDtoList(List<YearDto> yearDtoList) {
        this.yearDtoList = yearDtoList;
    }

    public List<CourseDto> getCourseDtoList() {
        this.courseDtoList = courseService.listCourse();
        return courseDtoList;
    }

    public void setCourseDtoList(List<CourseDto> courseDtoList) {
        this.courseDtoList = courseDtoList;
    }

    public SemesterService getSemesterService() {
        return semesterService;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    public YearService getYearService() {
        return yearService;
    }

    public void setYearService(YearService yearService) {
        this.yearService = yearService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
