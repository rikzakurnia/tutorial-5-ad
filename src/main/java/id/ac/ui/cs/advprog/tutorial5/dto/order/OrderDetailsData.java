package id.ac.ui.cs.advprog.tutorial5.dto.order;

import id.ac.ui.cs.advprog.tutorial5.model.order.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsData {
    private Integer medicineId;
    private Integer quantity;
    private Integer totalPrice;

    public static OrderDetailsData fromOrderDetails(OrderDetails orderDetails) {
        return OrderDetailsData.builder()
                .medicineId(orderDetails.getMedicine().getId())
                .quantity(orderDetails.getQuantity())
                .totalPrice(orderDetails.getTotalPrice())
                .build();
    }
}
