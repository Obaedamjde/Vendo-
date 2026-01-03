package com.aabu.finalproject.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "cart_items" ,
        indexes = {
                @Index(name="ix_cart_items_cart", columnList="cart_id"),
                @Index(name="ix_cart_items_product", columnList="product_id")
        } ,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "ux_cart_item_cart_product",
                        columnNames = {"cart_id", "product_id"}
                )
        }

)


@Check(constraints = "quantity > 0 AND unit_price >= 0")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItemEntity extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;


    @ToString.Exclude
    @JsonBackReference("cartItem-Cart")
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "cart_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cartItem_cart")
    )
    private CartEntity cartEntity;


    @ToString.Exclude
    @JsonBackReference("cartItem-product")
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cartItem_product")
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
