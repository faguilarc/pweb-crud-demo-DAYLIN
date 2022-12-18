package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.Drop_out_causeDto;

import java.util.List;

public interface Drop_out_causeService {
    List<Drop_out_causeDto> getDrop_out_cause();
    Drop_out_causeDto getDrop_out_causeById(String id);
    void createDrop_out_cause(Drop_out_causeDto drop_out_cause);
    void updateDrop_out_cause(Drop_out_causeDto drop_out_cause);
    void deleteDrop_out_cause(String id);
}
