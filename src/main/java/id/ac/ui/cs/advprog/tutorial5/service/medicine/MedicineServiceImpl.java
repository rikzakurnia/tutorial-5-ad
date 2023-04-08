package id.ac.ui.cs.advprog.tutorial5.service.medicine;

import id.ac.ui.cs.advprog.tutorial5.dto.MedicineRequest;
import id.ac.ui.cs.advprog.tutorial5.exceptions.MedicineDoesNotExistException;
import id.ac.ui.cs.advprog.tutorial5.model.medicine.Medicine;
import id.ac.ui.cs.advprog.tutorial5.model.medicine.MedicineCategory;
import id.ac.ui.cs.advprog.tutorial5.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;
    @Override
    public List<Medicine> findAll() {
        // TODO: Lengkapi kode berikut
        return medicineRepository.findAll();
    }

    @Override
    public Medicine findById(Integer id) {
        // TODO: Lengkapi kode berikut (Pastikan Anda memanfaatkan Exceptions yang ada!)
        if(isMedicineDoesNotExist(id)){
            throw new MedicineDoesNotExistException(id);
        }
        Medicine medicine = medicineRepository.findById(id).get();
        return medicine;
    }

    @Override
    public Medicine create(MedicineRequest request) {
        // TODO: Lengkapi kode berikut
        Medicine medicine = null;
        medicine = Medicine.builder()
                .name(request.getName())
                .category(MedicineCategory.valueOf(request.getCategory()))
                .dose(request.getDose())
                .stock(request.getStock())
                .price(request.getPrice())
                .manufacturer(request.getManufacturer())
                .build();
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine update(Integer id, MedicineRequest request) {
        // TODO: Lengkapi kode berikut (Pastikan Anda memanfaatkan Exceptions yang ada!)
        if (isMedicineDoesNotExist(id)) {
            throw new MedicineDoesNotExistException(id);
        }
        Medicine medicine = null;
        medicine = medicineRepository.findById(id).get();
        medicine.setName(request.getName());
        medicine.setCategory(MedicineCategory.valueOf(request.getCategory()));
        medicine.setDose(request.getDose());
        medicine.setPrice(request.getPrice());
        medicine.setStock(request.getStock());
        medicine.setManufacturer(request.getManufacturer());
        return this.medicineRepository.save(medicine);
    }

    @Override
    public void delete(Integer id) {
        // TODO: Lengkapi kode berikut (Pastikan Anda memanfaatkan Exceptions yang ada!)
        if(isMedicineDoesNotExist(id)){
            throw new MedicineDoesNotExistException(id);
        }
        medicineRepository.deleteById(id);
    }

    private boolean isMedicineDoesNotExist(Integer id) {
        return medicineRepository.findById(id).isEmpty();
    }
}
