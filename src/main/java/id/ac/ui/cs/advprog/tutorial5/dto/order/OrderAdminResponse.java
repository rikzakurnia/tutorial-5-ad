package id.ac.ui.cs.advprog.tutorial5.dto.order;

import id.ac.ui.cs.advprog.tutorial5.model.order.Order;
import id.ac.ui.cs.advprog.tutorial5.model.order.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAdminResponse {
    private Integer userId;
    private Integer orderId;
    private Date orderDate;
    private List<OrderDetailsData> orderDetailsData;

    public static OrderAdminResponse fromOrder(Order order, List<OrderDetails> orderDetails) {
        return OrderAdminResponse.builder()
                .userId(order.getUser().getId())
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .orderDetailsData(orderDetails
                        .stream()
                        .map(OrderDetailsData::fromOrderDetails)
                        .toList())
                .build();
    }
}
