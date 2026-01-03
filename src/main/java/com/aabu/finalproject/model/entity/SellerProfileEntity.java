package com.aabu.finalproject.model.entity;


import com.aabu.finalproject.model.enums.SellerProfileStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.BatchSize;

import java.util.*;

@Entity
@Table(name = "seller_profiles" , uniqueConstraints =
        {
                @UniqueConstraint(name = "uq_seller_profiles_user",columnNames = "user_id"),
                @UniqueConstraint(name ="uq_seller_profiles_ig_handle" , columnNames = "instagram_handle"),
                @UniqueConstraint(name ="uq_seller_profiles_ig_url" , columnNames = "instagram_url")

        },
        indexes = {
             @Index(name = "ix_seller_profiles_status_created", columnList = "status_seller,created_at")
        }

        )


@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerProfileEntity extends BaseEntity {

    @Id @GeneratedValue(strategy =GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    @Column(name = "seller_id")
    private Long id ;

    @JsonManagedReference("seller-user")
    @ToString.Exclude
    @OneToOne(optional = false , fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false ,
            unique = true,
            foreignKey =@ForeignKey (name =  "fk_seller_profiles_user")
    )
    private UserEntity userEntity;


    @JsonManagedReference("products-seller")
    @ToString.Exclude
    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "seller",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL ,
            orphanRemoval = true)
    private Set<ProductEntity> productEntities =new LinkedHashSet<>();


    @JsonManagedReference("order-Seller")
    @ToString.Exclude
    @BatchSize(size = 50)
    @OneToMany(
            cascade = CascadeType.ALL ,
            fetch = FetchType.LAZY,
            orphanRemoval = true ,
            mappedBy = "seller"
    )
    private Set<OrderEntity> orderEntities =new LinkedHashSet<>();



    @NotBlank
    @Column(name = "shop_name" ,nullable = false ,unique = true ,length = 100)
    private String shopName ;


    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._]{3,30}$", message = "Instagram handle must be 3-30 chars, letters, digits, dot or underscore just ")
    @Column(name = "instagram_handle" ,nullable = false ,length = 100)
    private String instagramHandle ;

    @org.hibernate.validator.constraints.URL
    @NotBlank
    @Column(name = "instagram_url" ,nullable = false ,length = 255)
    private String instagramUrl ;

    @NotBlank
    @Column(columnDefinition = "Text")
    private String bio;


    @NotNull
    @Column(name = "confirm_order" ,nullable = false)
    private int confirmOrder;



    @Builder.Default
    @Column( name = "status_seller", nullable=false, length=10)
    @Enumerated(value = EnumType.STRING)
    private SellerProfileStatus status = SellerProfileStatus.PENDING;


    public void attachUser(UserEntity newUserEntity){

        if(this.userEntity == newUserEntity) return;

        if(this.userEntity !=null){
            UserEntity old =this.userEntity;
            this.userEntity =null;
            if(old.getSeller()==this){
                old.setSeller(null);
            }
        }

        this.userEntity = newUserEntity;
        if(newUserEntity !=null && newUserEntity.getSeller()!=this){
            newUserEntity.setSeller(this);
        }
    }

    public void deAttachUser(){
        attachUser(null);
    }

}
