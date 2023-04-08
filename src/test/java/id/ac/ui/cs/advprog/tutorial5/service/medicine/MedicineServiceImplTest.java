package id.ac.ui.cs.advprog.tutorial5.service.medicine;

import id.ac.ui.cs.advprog.tutorial5.dto.MedicineRequest;
import id.ac.ui.cs.advprog.tutorial5.exceptions.MedicineDoesNotExistException;
import id.ac.ui.cs.advprog.tutorial5.model.medicine.Medicine;
import id.ac.ui.cs.advprog.tutorial5.model.medicine.MedicineCategory;
import id.ac.ui.cs.advprog.tutorial5.repository.MedicineRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    @InjectMocks
    private MedicineServiceImpl service;

    @Mock
    private MedicineRepository repository;

    Medicine medicine;
    Medicine newMedicine;
    MedicineRequest createRequest;
    MedicineRequest updateRequest;

    @BeforeEach
    void setUp() {
        createRequest = MedicineRequest.builder()
                .name("Hayase Yuuka")
                .dose("Hayase Yuuka")
                .category("NARCOTIC_MEDICINE")
                .stock(100)
                .price(100)
                .manufacturer("Hayase Yuuka")
                .build();

        updateRequest = MedicineRequest.builder()
                .name("Ushio Noa")
                .dose("Ushio Noa")
                .category("NARCOTIC_MEDICINE")
                .stock(100)
                .price(100)
                .manufacturer("Ushio Noa")
                .build();


        medicine = Medicine.builder()
                .id(0)
                .name("Hayase Yuuka")
                .dose("Hayase Yuuka")
                .category(MedicineCategory.NARCOTIC_MEDICINE)
                .stock(100)
                .price(100)
                .manufacturer("Hayase Yuuka")
                .build();

        newMedicine = Medicine.builder()
                .id(0)
                .name("Ushio Noa")
                .dose("Ushio Noa")
                .category(MedicineCategory.NARCOTIC_MEDICINE)
                .stock(100)
                .price(100)
                .manufacturer("Ushio Noa")
                .build();
    }

    @Test
    void whenFindAllMedicineShouldReturnListOfMedicines() {
        List<Medicine> allMedicines = List.of(medicine);

        when(repository.findAll()).thenReturn(allMedicines);

        List<Medicine> result = service.findAll();
        verify(repository, atLeastOnce()).findAll();
        Assertions.assertEquals(allMedicines, result);
    }

    @Test
    void whenFindByIdAndFoundShouldReturnMedicine() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(medicine));

        Medicine result = service.findById(0);
        verify(repository, atLeastOnce()).findById(any(Integer.class));
        Assertions.assertEquals(medicine, result);
    }

    @Test
    void whenFindByIdAndNotFoundShouldThrowException() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(MedicineDoesNotExistException.class, () -> {
            service.findById(0);
        });
    }

    @Test
    void whenCreateMedicineShouldReturnTheCreatedMedicine() {
        when(repository.save(any(Medicine.class))).thenAnswer(invocation -> {
            var medicine = invocation.getArgument(0, Medicine.class);
            medicine.setId(0);
            return medicine;
        });

        Medicine result = service.create(createRequest);
        verify(repository, atLeastOnce()).save(any(Medicine.class));
        Assertions.assertEquals(medicine, result);
    }

    @Test
    void whenUpdateMedicineAndFoundShouldReturnTheUpdatedMedicine() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(medicine));
        when(repository.save(any(Medicine.class))).thenAnswer(invocation ->
                invocation.getArgument(0, Medicine.class));

        Medicine result = service.update(0, updateRequest);
        verify(repository, atLeastOnce()).save(any(Medicine.class));
        Assertions.assertEquals(newMedicine, result);
    }

    @Test
    void whenUpdateMedicineAndNotFoundShouldThrowException() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(MedicineDoesNotExistException.class, () -> {
            service.update(0, createRequest);
        });
    }

    @Test
    void whenDeleteMedicineAndFoundShouldCallDeleteByIdOnRepo() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(medicine));

        service.delete(0);
        verify(repository, atLeastOnce()).deleteById(any(Integer.class));
    }

    @Test
    void whenDeleteMedicineAndNotFoundShouldThrowException() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(MedicineDoesNotExistException.class, () -> {
            service.delete(0);
        });
    }

}