package com.aabu.finalproject.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Entity
@Table(name = "carts" ,
        indexes = @Index(name = "ix_carts_user", columnList = "user_id"),
        uniqueConstraints = @UniqueConstraint(name = "uq_carts_user", columnNames = "user_id")
)

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder

public class CartEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;

    @ToString.Exclude
    @BatchSize(size = 50)
    @JsonManagedReference("cartItem-Cart")
    @OneToMany(cascade = CascadeType.ALL ,
            fetch = FetchType.LAZY,
            orphanRemoval = true ,
            mappedBy = "cartEntity"
    )
    private Set<CartItemEntity> cartItemEntities =new LinkedHashSet<>();


    @ToString.Exclude
    @JsonBackReference("cart-user")
    @OneToOne(fetch = FetchType.LAZY ,optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_cart_user")
    )
    private UserEntity userEntity;

}
