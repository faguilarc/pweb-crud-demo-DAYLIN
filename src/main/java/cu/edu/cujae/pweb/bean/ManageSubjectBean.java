package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.GroupsDto;
import cu.edu.cujae.pweb.dto.SemesterDto;
import cu.edu.cujae.pweb.dto.SubjectDto;
import cu.edu.cujae.pweb.service.SemesterService;
import cu.edu.cujae.pweb.service.SubjectService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ManagedBean
@ViewScoped
@Component
public class ManageSubjectBean {

    private SubjectDto selectedSubject;
    private List<SubjectDto> subjectDtoList;
    private String selectedSemester;

    private List<SemesterDto> semesterDtoList;

    @Autowired
    private SemesterService semesterService;
    @Autowired
    private SubjectService subjectService;

    public ManageSubjectBean() {
        subjectDtoList = new ArrayList<>();
        semesterDtoList = new ArrayList<>();
    }

    public void openNew(){
        this.selectedSubject = new SubjectDto();
    }

    public void saveSemester() {
        if (this.selectedSubject.getId_subject() == null) {
            this.selectedSubject.setId_subject(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));

            this.selectedSubject.setId_semester(this.selectedSemester);

            subjectService.createSubject(this.selectedSubject);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_subject_added");
            System.out.println("Ya llame al servicio de crear");
        } else {
            subjectService.updateSubject(this.selectedSubject);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_subject_edited");
        }
        PrimeFaces.current().executeScript("PF('manageSubjectDialog').hide()");
        refresh();
        PrimeFaces.current().ajax().update("form:dt-subjects");
    }


    public void deleteSubject(){
        try {
            subjectService.deleteSubject(this.selectedSubject.getId_subject());
            refresh();
            this.selectedSubject = new SubjectDto();
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_subject_deleted");

            PrimeFaces.current().ajax().update("form:dt-subjects");
        }catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public SubjectDto getSelectedSubject() {
        return selectedSubject;
    }

    public void setSelectedSubject(SubjectDto selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    public List<SubjectDto> getSubjectDtoList() {
        subjectDtoList = subjectService.getSubject();
        return subjectDtoList;
    }

    public void setSubjectDtoList(List<SubjectDto> subjectDtoList) {
        this.subjectDtoList = subjectDtoList;
    }

    public String getSelectedSemester() {
        return selectedSemester;
    }

    public void setSelectedSemester(String selectedSemester) {
        this.selectedSemester = selectedSemester;
    }

    public List<SemesterDto> getSemesterDtoList() {
        semesterDtoList = semesterService.listSemester();
        return semesterDtoList;
    }

    public void setSemesterDtoList(List<SemesterDto> semesterDtoList) {
        this.semesterDtoList = semesterDtoList;
    }

    public SemesterService getSemesterService() {
        return semesterService;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    private void refresh() {
        subjectDtoList = subjectService.getSubject();
    }

}
