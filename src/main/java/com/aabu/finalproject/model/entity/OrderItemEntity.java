package com.aabu.finalproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "order_items" ,
        indexes = {
                @Index(name="ix_order_items_order", columnList="order_id"),
                @Index(name="ix_order_items_product", columnList="product_id")
        }
)



@Check(constraints = "quantity > 0 AND unit_price >= 0")
@Setter  @Getter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;


    @ToString.Exclude
    @JsonBackReference("orderItem-order")
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_orderItem_order")
    )
    private OrderEntity orderEntity;


    @ToString.Exclude
    @JsonBackReference("orderItem-product")
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_orderItem_product")
    )
    private ProductEntity productEntity;


    @NotNull
    @Column(name = "quantity" ,nullable = false)
    private Integer quantity;


    @PositiveOrZero
    @NotNull
    @Column(name = "unit_price" ,nullable = false , precision = 10, scale = 2)
    private java.math.BigDecimal unitPrice;


}
