package id.ac.ui.cs.advprog.tutorial5.model.order;

import id.ac.ui.cs.advprog.tutorial5.model.medicine.Medicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Medicine medicine;
    private Integer quantity;
    private Integer totalPrice;
}
