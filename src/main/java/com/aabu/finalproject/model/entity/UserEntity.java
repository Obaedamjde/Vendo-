    package com.aabu.finalproject.model.entity;


    import com.aabu.finalproject.model.enums.Role;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Pattern;
    import lombok.*;
    import org.hibernate.annotations.BatchSize;

    import java.util.LinkedHashSet;
    import java.util.Set;


    @Entity
    @Table(name = "users" ,
            indexes = @Index(name = "ix_users_role_created", columnList = "role,created_at")
    )

    @Setter @Getter
    @AllArgsConstructor @NoArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @Builder

    public class UserEntity extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        @EqualsAndHashCode.Include
        private Long id;


        @JsonManagedReference("order-User")
        @ToString.Exclude
        @BatchSize(size = 50)
        @OneToMany(
                mappedBy = "userEntity",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL ,
                orphanRemoval = true)
        private Set<OrderEntity> orderEntities =new LinkedHashSet<>();

        @JsonManagedReference("favorite-user")
        @ToString.Exclude
        @BatchSize(size = 50)
        @OneToMany(
                mappedBy = "userEntity",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL ,
                orphanRemoval = true)
        private Set<FavoriteEntity> favoriteEntities =new LinkedHashSet<>();


        @JsonManagedReference("cart-user")
        @ToString.Exclude
        @OneToOne (
                mappedBy = "userEntity",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL ,
                orphanRemoval = true
        )
        private CartEntity cartEntity;



        @JsonManagedReference("seller-user")
        @ToString.Exclude
        @OneToOne (
                mappedBy = "userEntity",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL ,
                orphanRemoval = true
        )
        private SellerProfileEntity seller;


//        @OneToOne(mappedBy = "userEntity" ,fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
//        private SellerProfileEntity seller;




        @NotBlank
        @Column(name ="full_name" , nullable = false ,  length = 150)
        private String fullName;


        @NotBlank
        @Email(message ="Invalid email format" )
        @Column( name = "email" , unique = true , length=190)
        private String email;


        @NotNull
        @Pattern(regexp = "^07[0-9]{8}$" ,message = "Invalid phone number, must be 10 digits starting with 07")
        @Column( name = "phone", length=10 ,nullable=false,  unique = true )
        private String phone;


        @Builder.Default
        @Enumerated(EnumType.STRING)
        @Column(name = "role", length=15)
        private Role role=Role.BUYER;



        @NotBlank
        @JsonIgnore
        @Column(name="password_hash", nullable=false, length=255)
        private String passwordHash;


        @Builder.Default
        @Column(name="is_verified", nullable=false)
        private boolean isVerified = false;


        @Builder.Default
        @Column(name = "blocked_user",nullable=false)
        private boolean blocked_user = false;



    }
