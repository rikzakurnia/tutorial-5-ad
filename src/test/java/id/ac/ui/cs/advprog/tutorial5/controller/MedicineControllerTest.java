package id.ac.ui.cs.advprog.tutorial5.controller;

import id.ac.ui.cs.advprog.tutorial5.Util;
import id.ac.ui.cs.advprog.tutorial5.dto.MedicineRequest;
import id.ac.ui.cs.advprog.tutorial5.model.medicine.Medicine;
import id.ac.ui.cs.advprog.tutorial5.model.medicine.MedicineCategory;
import id.ac.ui.cs.advprog.tutorial5.service.JwtService;
import id.ac.ui.cs.advprog.tutorial5.service.medicine.MedicineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MedicineController.class)
@AutoConfigureMockMvc
class MedicineControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MedicineServiceImpl service;

    @MockBean
    private JwtService jwtService;

    Medicine medicine;
    Object bodyContent;

    @BeforeEach
    void setUp() {
        medicine = Medicine.builder()
                .name("Hayase Yuuka")
                .category(MedicineCategory.NARCOTIC_MEDICINE)
                .dose("Hayase Yuuka")
                .stock(100)
                .price(100)
                .manufacturer("Hayase Yuuka")
                .build();

        bodyContent = new Object() {
            public final String name = "Hayase Yuuka";
            public final String category = "NARCOTIC_MEDICINE";
            public final String dose = "Hayase Yuuka";
            public final Integer stock = 100;
            public final Integer price = 100;
            public final String manufacturer = "Hayase Yuuka";
        };
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllMedicine() throws Exception {
        List<Medicine> allMedicines = List.of(medicine);

        when(service.findAll()).thenReturn(allMedicines);

        mvc.perform(get("/api/v1/medicine/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllMedicine"))
                .andExpect(jsonPath("$[0].name").value(medicine.getName()));

        verify(service, atLeastOnce()).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetMedicineById() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(medicine);

        mvc.perform(get("/api/v1/medicine/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getMedicineById"))
                .andExpect(jsonPath("$.name").value(medicine.getName()));

        verify(service, atLeastOnce()).findById(any(Integer.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddMedicine() throws Exception {
        when(service.create(any(MedicineRequest.class))).thenReturn(medicine);

        mvc.perform(post("/api/v1/medicine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.mapToJson(bodyContent))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("addMedicine"))
                .andExpect(jsonPath("$.name").value(medicine.getName()));

        verify(service, atLeastOnce()).create(any(MedicineRequest.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testPutMedicine() throws Exception {
        when(service.update(any(Integer.class), any(MedicineRequest.class))).thenReturn(medicine);

        mvc.perform(put("/api/v1/medicine/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.mapToJson(bodyContent))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("putMedicine"))
                .andExpect(jsonPath("$.name").value(medicine.getName()));

        verify(service, atLeastOnce()).update(any(Integer.class), any(MedicineRequest.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteMedicine() throws Exception {
        mvc.perform(delete("/api/v1/medicine/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("deleteMedicine"));

        verify(service, atLeastOnce()).delete(any(Integer.class));
    }
}
