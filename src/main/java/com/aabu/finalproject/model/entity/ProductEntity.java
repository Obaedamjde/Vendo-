package com.aabu.finalproject.model.entity;


import com.aabu.finalproject.model.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_product_unique_per_seller_category",
                        columnNames = {"seller_id", "category_id", "title"}
                )
        },

        indexes = {
                @Index(name="ix_products_seller_status", columnList="seller_id,status"),
                @Index(name="ix_products_category_status", columnList="category_id,status")
        }

)

@Check(constraints = "price>0 AND quantity >0")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductEntity extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;


    @JsonBackReference("products-seller")
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "seller_id",
            nullable = false,
          foreignKey =  @ForeignKey (name = "fk_product_seller")
    )
    private SellerProfileEntity seller;


    @JsonBackReference("product-category")
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_cat")
    )
    private CategoryEntity categoryEntity;


    @JsonManagedReference("image-product")
    @ToString.Exclude
    @BatchSize(size = 50)
    @OneToMany(cascade = CascadeType.ALL ,
            fetch = FetchType.LAZY,
            orphanRemoval = true ,
            mappedBy = "productEntity"
    )
    @OrderBy("sortOrder ASC")
    private List<ProductImageEntity> images=new ArrayList<>();


    @JsonManagedReference("cartItem-product")
    @ToString.Exclude
    @BatchSize(size = 50)
    @OneToMany(cascade = CascadeType.ALL ,
            fetch = FetchType.LAZY,
            orphanRemoval = true ,
            mappedBy = "productEntity"
    )
    private Set<CartItemEntity> productsCart =new LinkedHashSet<>();


    @ToString.Exclude
    @BatchSize(size = 50)
    @JsonManagedReference("orderItem-product")
    @OneToMany(fetch = FetchType.LAZY ,
            orphanRemoval = true,
            cascade = CascadeType.ALL ,
            mappedBy = "productEntity"
    )
    private Set<OrderItemEntity> productsOrder =new LinkedHashSet<>();


    @ToString.Exclude
    @BatchSize(size = 50)
    @JsonManagedReference("favorite-product")
    @OneToMany(fetch = FetchType.LAZY ,
            orphanRemoval = true,
            cascade = CascadeType.ALL ,
            mappedBy = "productEntity"
    )
    private Set<FavoriteEntity> favoriteEntities =new LinkedHashSet<>();


    @NotBlank
    @Column(name = "title", nullable=false, length=200)
    private String title;


    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @NotNull
    @Positive
    @Column( name = "price", nullable=false, precision=10, scale=2)
    private java.math.BigDecimal price;


    @NotNull
    @Positive
    @Builder.Default
    @Column(name = "quantity", nullable=false)
    private Integer quantity = 0;


    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=15)
    private ProductStatus status = ProductStatus.SOLD_IN;



    public void addImage(ProductImageEntity img) {
        if (img == null) return;
        images.add(img);
        img.setProductEntity(this);
    }

    public void removeImage(ProductImageEntity img) {
        if (img == null) return;
        images.remove(img);
        img.setProductEntity(null);
    }
}
