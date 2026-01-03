package com.aabu.finalproject.model.repository;

import com.aabu.finalproject.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

    //@EntityGraph(attributePaths = {"images"}) استخدم هاي مع اي جدول بكون في علاقات list فيه
    // 1 product -> many  pic ---> to avoid 1+N problem
    // لما تعمل جلب لكل المنتجات لنفرض انهم 100
    // هاد يعتبر استعلام واحد وبروح عند كل منتج بجيب الصور تبعته يعني صار عندك 101 عمليه استعلام وهاد سيئ

    @Query(
            value = """
            
            SELECT p.*
            FROM products p
            WHERE
              ( :q IS NULL OR :q = ''
                OR MATCH(p.title, p.description)
                   AGAINST (:q IN NATURAL LANGUAGE MODE)
              )
              AND ( :categoryId IS NULL
                    OR p.category_id = :categoryId
              )
              AND ( :minPrice IS NULL
                    OR p.price >= :minPrice
              )
              AND ( :maxPrice IS NULL
                    OR p.price <= :maxPrice
              )
            ORDER BY
              CASE
                WHEN :q IS NULL OR :q = '' THEN 0
                ELSE MATCH(p.title, p.description)
                     AGAINST (:q IN NATURAL LANGUAGE MODE)
                                         
              END DESC,
            p.id DESC
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM products p
            WHERE
              ( :q IS NULL OR :q = ''
                OR MATCH(p.title, p.description)
                   AGAINST (:q IN NATURAL LANGUAGE MODE)
              )
              AND ( :categoryId IS NULL
                    OR p.category_id = :categoryId
              )
              AND ( :minPrice IS NULL
                    OR p.price >= :minPrice
              )
              AND ( :maxPrice IS NULL
                    OR p.price <= :maxPrice
              )
            """,
            nativeQuery = true
    )
    Page<ProductEntity> searchProducts(
            @Param("q") String q,
            @Param("categoryId") Integer categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );


    boolean existsById(Integer integer);

    @Override
    Optional<ProductEntity> findById(Integer integer);


    void deleteById(Integer id);

    long countByIdInAndSeller_IdNot(List<Integer> productIds, Long sellerId);// كم منتج مش تابع لنفس البائع


    @Modifying
    @Query("""
        UPDATE ProductEntity p
        SET p.quantity = p.quantity - :qty
        WHERE p.id = :productId
          AND p.quantity >= :qty
    """)
    int decrementStockIfAvailable(@Param("productId") Integer productId,
                                  @Param("qty") Integer qty);


    List<ProductEntity> getAllBySeller_Id(Long sellerId);

    @Modifying
    @Query("""  
        UPDATE ProductEntity p
        SET p.quantity = p.quantity + :qty
        WHERE p.id = :productId
    """)
    int incrementStockAfterCancel(@Param("productId") Integer productId,
                                      @Param("qty") Integer qty);

}
