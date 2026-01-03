    package com.aabu.finalproject.model.entity;


    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.PositiveOrZero;
    import lombok.*;
    import org.hibernate.validator.constraints.URL;

    @Entity
    @Table(name = "product_images" ,
            indexes = { @Index(name="ix_product_images_product", columnList="product_id") } ,
    uniqueConstraints = @UniqueConstraint(name = "uq_product_images_product_sort",
            columnNames = {"product_id", "sort_order"})

    )



    @Setter @Getter
    @AllArgsConstructor @NoArgsConstructor
    @ToString
    @Builder
    public class ProductImageEntity extends BaseEntity{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        @EqualsAndHashCode.Include
        private Long id;


        @JsonBackReference("image-product")
        @ToString.Exclude
        @ManyToOne(fetch = FetchType.LAZY ,optional = false)
        @JoinColumn(
                name = "product_id",
                nullable = false ,
                foreignKey = @ForeignKey(name = "fk_img_product")
        )
        private ProductEntity productEntity;

        @URL
        @Column(name="img_url", nullable=false, length=512)
        private String imgUrl;        // Cloudinary secure_url


        @NotBlank
        @Column(name="public_id", nullable=false, length=255)
        private String publicId;      // Cloudinary public_id


        @Builder.Default
        @Column(name="is_primary", nullable=false)
        private boolean isPrimary = false;  // غلاف


        @PositiveOrZero
        @Column(name="sort_order", nullable=false)
        private Integer sortOrder ;
    }
