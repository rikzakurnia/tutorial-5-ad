package id.ac.ui.cs.advprog.tutorial5.controller;

import id.ac.ui.cs.advprog.tutorial5.Util;
import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderAdminResponse;
import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderRequest;
import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderUserResponse;
import id.ac.ui.cs.advprog.tutorial5.model.auth.User;
import id.ac.ui.cs.advprog.tutorial5.model.order.Order;
import id.ac.ui.cs.advprog.tutorial5.service.JwtService;
import id.ac.ui.cs.advprog.tutorial5.service.order.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc
class OrderControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private OrderServiceImpl service;

    @MockBean
    private JwtService jwtService;

    @Mock
    User user;

    Order order;
    Object bodyContent;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        order = Order.builder()
                .id(1)
                .build();

        bodyContent = new Object() {
            public final Integer id = 1;
        };
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllOrder() throws Exception {
        OrderAdminResponse orderAdminResponse = new OrderAdminResponse();
        orderAdminResponse.setOrderId(1);

        when(service.findAll()).thenReturn(List.of(orderAdminResponse));

        mvc.perform(get("/api/v1/order/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllOrder"))
                .andExpect(jsonPath("$[0].orderId").value(String.valueOf(orderAdminResponse.getOrderId())));

        verify(service, atLeastOnce()).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllUserOrder() throws Exception {
        OrderUserResponse orderUserResponse = new OrderUserResponse();
        orderUserResponse.setOrderId(1);

        when(service.findAllByUserId(any(Integer.class))).thenReturn(List.of(orderUserResponse));

        mvc.perform(get("/api/v1/order/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllUserOrder"))
                .andExpect(jsonPath("$[0].orderId").value(String.valueOf(orderUserResponse.getOrderId())));

        verify(service, atLeastOnce()).findAllByUserId(any(Integer.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateOrder() throws Exception {
        when(service.create(any(Integer.class), any(OrderRequest.class))).thenReturn(order);

        mvc.perform(post("/api/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.mapToJson(bodyContent))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createOrder"))
                .andExpect(jsonPath("$.id").value(String.valueOf(order.getId())));

        verify(service, atLeastOnce()).create(any(Integer.class), any(OrderRequest.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateOrder() throws Exception {
        when(service.update(any(Integer.class), any(Integer.class), any(OrderRequest.class))).thenReturn(order);

        mvc.perform(put("/api/v1/order/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.mapToJson(bodyContent))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("updateOrder"))
                .andExpect(jsonPath("$.id").value(String.valueOf(order.getId())));

        verify(service, atLeastOnce()).update(any(Integer.class), any(Integer.class), any(OrderRequest.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteOrder() throws Exception {
        mvc.perform(delete("/api/v1/order/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("deleteOrder"));

        verify(service, atLeastOnce()).delete(any(Integer.class));
    }

}