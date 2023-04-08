package id.ac.ui.cs.advprog.tutorial5.service.medicine;

import id.ac.ui.cs.advprog.tutorial5.dto.MedicineRequest;
import id.ac.ui.cs.advprog.tutorial5.model.medicine.Medicine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineService {
    List<Medicine> findAll();
    Medicine findById(Integer id);
    Medicine create(MedicineRequest request);
    Medicine update(Integer id, MedicineRequest request);
    void delete(Integer id);
}
