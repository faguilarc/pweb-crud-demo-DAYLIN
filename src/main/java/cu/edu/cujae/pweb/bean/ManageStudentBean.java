package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.StudentDto;
import cu.edu.cujae.pweb.dto.TownDto;
import cu.edu.cujae.pweb.service.StudentService;
import cu.edu.cujae.pweb.service.TownService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@ManagedBean
@ViewScoped
public class ManageStudentBean {
    private StudentDto selectedStudent;
    private List<StudentDto> studentDtoList;
    private String selectedTown;

    private List<TownDto> townDtoList;

    @Autowired
    private StudentService studentService;
    @Autowired
    private TownService townService;

    public ManageStudentBean(){
        this.townDtoList = new ArrayList<TownDto>();
        this.studentDtoList = new ArrayList<>();

    }

    public void openNew(){
        this.selectedStudent = new StudentDto();

    }

    public void saveStudent() {
        if (this.selectedStudent.getId_student() == null) {
            this.selectedStudent.setId_student(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));

            this.selectedStudent.setId_town(this.selectedTown);

            studentService.createStudent(this.selectedStudent);
       //     this.selectedStudent.setTownDto(townService.getTownById(selectedTown));
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_student_added");
            System.out.println("Ya llame al servicio de crear");
        } else {
            studentService.updateStudent(this.selectedStudent);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_student_edited");
        }
        PrimeFaces.current().executeScript("PF('manageStudentDialog').hide()");
        refresh();
        PrimeFaces.current().ajax().update("form:dt-student");
    }

    public void deleteStudent(){
        try {
            studentService.deleteStudent(this.selectedStudent.getId_student());
            this.selectedStudent = new StudentDto();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_student_deleted");
            PrimeFaces.current().ajax().update("form:dt-student");
        }catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public void refresh(){
        studentDtoList = studentService.getStudent();
    }

    public StudentDto getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(StudentDto selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public List<StudentDto> getStudentDtoList() {
        this.studentDtoList = studentService.getStudent();
        return studentDtoList;
    }

    public void setStudentDtoList(List<StudentDto> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }

    public String getSelectedTown() {
        return selectedTown;
    }

    public void setSelectedTown(String selectedTown) {
        this.selectedTown = selectedTown;
    }

    public List<TownDto> getTownDtoList() {
        this.townDtoList = townService.getTowns();
        return townDtoList;
    }

    public void setTownDtoList(List<TownDto> townDtoList) {
        this.townDtoList = townDtoList;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public TownService getTownService() {
        return townService;
    }

    public void setTownService(TownService townService) {
        this.townService = townService;
    }

    public void openForEdit(){

    }
}
