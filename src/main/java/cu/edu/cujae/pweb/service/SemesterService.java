package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.SemesterDto;

import java.sql.SQLException;
import java.util.List;

public interface SemesterService {

    void createSemester(SemesterDto semester);

    void updateSemester(SemesterDto semester);

    List<SemesterDto> listSemester();

    SemesterDto getSemesterById(String id);

    void deleteSemester(String id);
}
