package com.aabu.finalproject.model.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table (name = "categories" ,

        uniqueConstraints = {
                @UniqueConstraint(name = "uq_categories_name", columnNames="category_name") ,
        } ,

        indexes = {
                @Index(name = "idx_categories_name", columnList = "category_name")
        }
)

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryEntity extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank
    @Column( name = "category_name", nullable=false, length=150)
    private String name;


    @ToString.Exclude
    @JsonManagedReference("category-subCategory")
    @OrderBy("name ASC ")
    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "categoryEntity",
            fetch = FetchType.LAZY
            , cascade = CascadeType.ALL
            ,orphanRemoval = true
    )
    private Set<SubCategoryEntity>subCategories=new LinkedHashSet<>();


    @JsonManagedReference("product-category")
    @ToString.Exclude
    @BatchSize(size = 50)
    @OneToMany(
            mappedBy = "categoryEntity",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL ,
            orphanRemoval = true)
    private Set<ProductEntity> productEntities =new LinkedHashSet<>();



    // Helpers
    public void addSubCategory(SubCategoryEntity sc) {
        if (sc == null) return;
        subCategories.add(sc);
        sc.setCategoryEntity(this);
    }
    public void removeSubCategory(SubCategoryEntity sc) {
        if (sc == null) return;
        subCategories.remove(sc);
        sc.setCategoryEntity(null);
    }

}
