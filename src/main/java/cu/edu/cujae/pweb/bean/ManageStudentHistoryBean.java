package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.*;
import cu.edu.cujae.pweb.service.CourseService;
import cu.edu.cujae.pweb.service.GroupsService;
import cu.edu.cujae.pweb.service.StudentHistoryService;
import cu.edu.cujae.pweb.service.StudentService;
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
public class ManageStudentHistoryBean {

    private StudentHistoryDto studentHistoryDto;
    private List<StudentHistoryDto> studentHistoryDtoList;
    private String idGroup;
    private String idCourse;
    private String idStudent;

    private List<GroupsDto> groupsDtoList;
    private List<CourseDto> courseDtoList;
    private List<StudentDto> studentDtoList;

    @Autowired
    private GroupsService groupsService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentHistoryService studentHistoryService;

    public ManageStudentHistoryBean() {
    }

    @PostConstruct
    public void init() {
        studentHistoryDtoList = studentHistoryService.listStudentHistory();

    }

    public void openNew() {
        this.studentHistoryDto = new StudentHistoryDto();

    }

    public void saveStudentHistory() {
        if (this.studentHistoryDto.getIdStudentHistory() == null) {
            this.studentHistoryDto.setIdStudentHistory(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));

            this.studentHistoryDto.setIdStudent(this.idStudent);
            this.studentHistoryDto.setIdCourse(this.idCourse);
            this.studentHistoryDto.setIdGroup(this.idGroup);

            studentHistoryService.createStudentHistory(this.studentHistoryDto);

            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_studentHistory_added"); //Este code permite mostrar un mensaje exitoso (FacesMessage.SEVERITY_INFO) obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
        }
        else {
            studentHistoryService.updateStudentHistory(this.studentHistoryDto);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_studentHistory_edited");
        }

        //load datatable again with new values
        studentHistoryDtoList = studentHistoryService.listStudentHistory();

        PrimeFaces.current().executeScript("PF('manageStudentHistoryDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-studentHistory");
    }

    public void deleteStudentHistory() {
        try {
            studentHistoryService.deleteStudentHistory(this.studentHistoryDto.getIdStudentHistory());
            this.studentHistoryDto = new StudentHistoryDto();
            studentHistoryDtoList = studentHistoryService.listStudentHistory();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_studentHistory_deleted");
            PrimeFaces.current().ajax().update("form:dt-studentHistory");// Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
        } catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }

    }

    public StudentHistoryDto getStudentHistoryDto() {
        return studentHistoryDto;
    }

    public void setStudentHistoryDto(StudentHistoryDto studentHistoryDto) {
        this.studentHistoryDto = studentHistoryDto;
    }

    public List<StudentHistoryDto> getStudentHistoryDtoList() {
        return studentHistoryDtoList;
    }

    public void setStudentHistoryDtoList(List<StudentHistoryDto> studentHistoryDtoList) {
        this.studentHistoryDtoList = studentHistoryDtoList;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public List<GroupsDto> getGroupsDtoList() {
        return groupsDtoList = groupsService.getGroups();
    }

    public void setGroupsDtoList(List<GroupsDto> groupsDtoList) {
        this.groupsDtoList = groupsDtoList;
    }

    public List<CourseDto> getCourseDtoList() {
        return courseDtoList = courseService.listCourse();
    }

    public void setCourseDtoList(List<CourseDto> courseDtoList) {
        this.courseDtoList = courseDtoList;
    }

    public List<StudentDto> getStudentDtoList() {
        return studentDtoList = studentService.getStudent();
    }

    public void setStudentDtoList(List<StudentDto> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }

    public GroupsService getGroupsService() {
        return groupsService;
    }

    public void setGroupsService(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public StudentHistoryService getStudentHistoryService() {
        return studentHistoryService;
    }

    public void setStudentHistoryService(StudentHistoryService studentHistoryService) {
        this.studentHistoryService = studentHistoryService;
    }
}
