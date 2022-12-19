package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.CourseDto;
import cu.edu.cujae.pweb.service.CourseService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@ManagedBean
@ViewScoped
@Component
public class ManageCourseBean {

    private CourseDto courseDto;
    private List<CourseDto> courseDtoList;

    @Autowired
    private CourseService courseService;

    public ManageCourseBean() {

        this.courseDto = new CourseDto();

    }

    public void openNew() {
        this.courseDto = new CourseDto();

    }

    public void saveCourse() throws SQLException {


        if (this.courseDto.getIdCourse() == null) {

            if (courseDto.getEndDate().before(courseDto.getBeginDate())) {


                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_fecha_error");

            } else {
                this.courseDto.setIdCourse(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
        //        this.courseDto.setBeginDate(DateUtilToDateSql.convertUtiltoSql(this.courseDto.getBeginDate()));
        //        this.courseDto.setEndDate(DateUtilToDateSql.convertUtiltoSql(this.courseDto.getEndDate()));

                courseService.createCourse(this.courseDto);
                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_course_added"); //Este code permite mostrar un mensaje exitoso (FacesMessage.SEVERITY_INFO) obteniendo el mensage desde el fichero de recursos, con la llave message_user_added

            }
        } else {

            courseService.updateCourse(this.courseDto);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_course_edited");
        }

        courseDtoList = courseService.listCourse();
        PrimeFaces.current().executeScript("PF('manageCourseDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-courses");
    }

    public void delete() {
        try {
            //delete
            courseService.deleteCourse(this.courseDto.getIdCourse());
            this.courseDto = null;

            //load datatable again with new values
            courseDtoList = courseService.listCourse();

            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_course_deleted");
            PrimeFaces.current().ajax().update("form:dt-courses");

        } catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public CourseDto getCourseDto() {
        return courseDto;
    }

    public void setCourseDto(CourseDto courseDto) {
        this.courseDto = courseDto;
    }

    public List<CourseDto> getCourseDtoList() throws SQLException {
        return courseService.listCourse();
    }

    public void setCourseDtoList(List<CourseDto> courseDtoList) {
        this.courseDtoList = courseDtoList;
    }
}
