package com.aabu.finalproject.model.entity;


import com.aabu.finalproject.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders" ,
        indexes = {
                @Index(name = "ix_orders_user", columnList = "user_id"),
                @Index(name = "ix_orders_seller", columnList = "seller_id"),
                @Index(name="ix_orders_user_created", columnList="user_id,created_at"),
                @Index(name="ix_orders_seller_status", columnList="seller_id,status,created_at")
        }

)

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;


    @ToString.Exclude
    @JsonBackReference("order-User")
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_orders_user")
    )
    private UserEntity userEntity;



    @ToString.Exclude
    @JsonBackReference("order-Seller")
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "seller_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_orders_seller")
    )
    private SellerProfileEntity seller;


    @ToString.Exclude
    @BatchSize(size = 50)
    @JsonManagedReference("orderItem-order")
    @OneToMany(fetch = FetchType.LAZY ,
            orphanRemoval = true,
            cascade = CascadeType.ALL ,
            mappedBy = "orderEntity"
    )
    private Set<OrderItemEntity> orderItemEntities =new LinkedHashSet<>();



    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "status" ,nullable = false)
    private OrderStatus orderStatus=OrderStatus.PENDING;


    @PositiveOrZero
    @NotNull
    @Column(name = "sub_total" ,nullable = false , precision = 10, scale = 2)
    private java.math.BigDecimal subTotal;


    @Column(name="buyer_note", columnDefinition="TEXT")
    private String buyerNote;

}
