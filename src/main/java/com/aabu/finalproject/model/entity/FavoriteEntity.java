package com.aabu.finalproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "favorites"

        ,uniqueConstraints = @UniqueConstraint(
                name = "ux_favorites_user_product" ,
        columnNames = {"user_id","product_id"}
        ),
        indexes = { @Index(name = "ix_favorites_user", columnList = "user_id"),
                  @Index(name = "ix_favorites_product", columnList = "product_id") }
)


@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class FavoriteEntity extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;


    @JsonBackReference("favorite-user")
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY ,optional = false )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_user")
    )
    private UserEntity userEntity;


    @JsonBackReference("favorite-product")
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_fav_product")
    )
    private ProductEntity productEntity;
}
