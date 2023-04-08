package id.ac.ui.cs.advprog.tutorial5.service.order;

import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderDetailsData;
import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderRequest;
import id.ac.ui.cs.advprog.tutorial5.exceptions.MedicineDoesNotExistException;
import id.ac.ui.cs.advprog.tutorial5.exceptions.OrderDoesNotExistException;
import id.ac.ui.cs.advprog.tutorial5.model.auth.User;
import id.ac.ui.cs.advprog.tutorial5.model.order.Order;
import id.ac.ui.cs.advprog.tutorial5.model.order.OrderDetails;
import id.ac.ui.cs.advprog.tutorial5.repository.MedicineRepository;
import id.ac.ui.cs.advprog.tutorial5.repository.OrderDetailsRepository;
import id.ac.ui.cs.advprog.tutorial5.repository.OrderRepository;
import id.ac.ui.cs.advprog.tutorial5.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MedicineRepository medicineRepository;

    User user;
    Order order;

    OrderDetails orderDetails;
    OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        user = new User();

        OrderDetailsData orderDetailsData = new OrderDetailsData();
        orderDetailsData.setMedicineId(0);
        orderDetailsData.setQuantity(0);
        orderDetailsData.setTotalPrice(0);
        orderRequest = new OrderRequest(List.of(orderDetailsData));

        order = new Order();
        orderDetails = new OrderDetails();
    }

    @Test
    void whenCreateOrderButMedicineNotFoundShouldThrowException() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(medicineRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(MedicineDoesNotExistException.class, () -> {
            service.create(0, orderRequest);
        });
    }

    @Test
    void whenUpdateOrderButNotFoundShouldThrowException() {
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(OrderDoesNotExistException.class, () -> {
            service.update(0, 0, orderRequest);
        });
    }

    @Test
    void whenUpdateOrderButMedicineNotFoundShouldThrowException() {
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.of(order));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(orderDetailsRepository.findAllByOrderId(any(Integer.class))).thenReturn(List.of(orderDetails));
        when(medicineRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(MedicineDoesNotExistException.class, () -> {
            service.update(0, 0, orderRequest);
        });
    }

    @Test
    void whenDeleteOrderAndFoundShouldCallDeleteByIdOnRepo() {
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.of(order));
        service.delete(0);
        verify(orderRepository, atLeastOnce()).deleteById(any(Integer.class));
    }

    @Test
    void whenDeleteOrderButNotFoundShouldThrowException() {
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(OrderDoesNotExistException.class, () -> {
            service.delete(0);
        });
    }

}