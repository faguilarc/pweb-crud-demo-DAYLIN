package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> getSubject();
    SubjectDto getSubjectById(String id);
    void createSubject(SubjectDto subject);
    void updateSubject(SubjectDto subject);
    void deleteSubject(String id);
}
