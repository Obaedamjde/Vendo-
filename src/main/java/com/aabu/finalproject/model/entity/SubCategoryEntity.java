package com.aabu.finalproject.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table (name = "sub_category" ,
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_sub_categories_name", columnNames = {"category_id", "sub_category_name"})
        },
        indexes = {
                @Index(name = "idx_sub_categories_category_id", columnList = "category_id"),
                @Index(name = "idx_sub_categories_name", columnList = "sub_category_name"),
        }
)


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubCategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;

    @JsonBackReference("category-subCategory")
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_sub_categories_category")
    )
    private CategoryEntity categoryEntity;


    @NotBlank
    @Column( name = "sub_category_name", nullable=false, length=150)
    private String name;



}
