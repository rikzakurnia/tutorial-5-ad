package id.ac.ui.cs.advprog.tutorial5.service.order;

import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderRequest;
import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderAdminResponse;
import id.ac.ui.cs.advprog.tutorial5.dto.order.OrderUserResponse;
import id.ac.ui.cs.advprog.tutorial5.model.order.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderAdminResponse> findAll();
    List<OrderUserResponse> findAllByUserId(Integer userId);
    Order create(Integer userId, OrderRequest orderRequest);
    Order update(Integer userId, Integer id, OrderRequest orderRequest);
    void delete(Integer id);
}
