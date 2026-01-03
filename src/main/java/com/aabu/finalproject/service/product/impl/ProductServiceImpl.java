package com.aabu.finalproject.service.product.impl;

import com.aabu.finalproject.model.dto.ProductDto;
import com.aabu.finalproject.model.dto.SearchResultItemDto;
import com.aabu.finalproject.model.dto.common.PageReqDTO;
import com.aabu.finalproject.model.dto.common.PageResponse;
import com.aabu.finalproject.model.dto.request.ProductImageReq;
import com.aabu.finalproject.model.dto.request.SearchProductDtoReq;
import com.aabu.finalproject.model.dto.updateReq.UpdateProductReq;
import com.aabu.finalproject.model.entity.CategoryEntity;
import com.aabu.finalproject.model.entity.ProductEntity;
import com.aabu.finalproject.model.entity.ProductImageEntity;
import com.aabu.finalproject.model.entity.SellerProfileEntity;
import com.aabu.finalproject.model.enums.ProductStatus;
import com.aabu.finalproject.model.exception.BadRequestException;
import com.aabu.finalproject.model.exception.NotFoundException;
import com.aabu.finalproject.model.mapper.ProductMapper;
import com.aabu.finalproject.model.repository.CategoryRepository;
import com.aabu.finalproject.model.repository.ProductImageRepository;
import com.aabu.finalproject.model.repository.ProductRepository;
import com.aabu.finalproject.model.repository.SellerProfileRepository;
import com.aabu.finalproject.service.product.ProductService;
import com.aabu.finalproject.utility.PageUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final int MAX_IMAGES_PER_PRODUCT = 3;

    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepo;
    private final ProductMapper productMapper;
    private final SellerProfileRepository sellerRepo;
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepository;


    @Override
    public PageResponse<SearchResultItemDto> search(SearchProductDtoReq req, PageReqDTO pageReqDTO) {

        Pageable pageable = PageUtil.toPageableNoSort(pageReqDTO);
        String Query = req.getQuery();
        if (Query != null) {
            Query.trim();
            if (Query.isEmpty())
                Query = null;
        }

        BigDecimal min = req.getMinPrice();
        BigDecimal max = req.getMaxPrice();
        if (min != null && max != null && min.compareTo(max) > 0) {
            BigDecimal tmp = min;
            min = max;
            max = tmp;
        }

        Page<ProductEntity> page = productRepository.searchProducts(
                Query,
                req.getCategoryId(),
                min,
                max,
                pageable
        );

        return PageUtil.toPageResponse(page, productMapper::toSearchResultItem);
    }

    @Override
    public List<ProductDto> getAllBySeller(Long seller_id) {

       return productMapper.toDtoList( productRepository.getAllBySeller_Id(seller_id));
    }

    @Override
    public void addPic(UpdateProductReq req , ProductEntity productEntity) {
        List<ProductImageReq> imgsReq= new ArrayList<>(Optional.ofNullable(req.getImages())
                .orElse(List.<ProductImageReq>of()));


        if (productEntity.getImages() == null) {
            productEntity.setImages(new ArrayList<>());
        }

        if(!imgsReq.isEmpty() && imgsReq.size()>MAX_IMAGES_PER_PRODUCT){

            throw new BadRequestException("TOO_MANY_IMAGES",
                        "Maximum " + MAX_IMAGES_PER_PRODUCT + " images allowed per product");
        }


        for (ProductImageReq ir : imgsReq) {
            if (ir.getPublicId() == null || ir.getPublicId().isBlank()) {
                throw new BadRequestException("INVALID_IMAGE", "public_id is required for each image");
            }
            if (ir.getImgUrl() == null || ir.getImgUrl().isBlank()) {
                throw new BadRequestException("INVALID_IMAGE", "img_url is required for each image");
            }
        }

        int indexPrimary=-1;

        for (int z=0;z<imgsReq.size();z++){
            if( Boolean.TRUE.equals(imgsReq.get(z).getPrimary())){ // to avoid NullPointerException
                indexPrimary =z;
                break;
            }
        }

        if(indexPrimary<0 && !imgsReq.isEmpty()) {

            indexPrimary=  IntStream.range(0,imgsReq.size()).boxed()
                    .min(Comparator.comparing(
                            (Integer i)->Optional.ofNullable(imgsReq.get(i).getSortOrder())
                                    .orElse(Integer.MAX_VALUE)) // for null
                    )
                    .orElse(0);
        }


        int auto=0;
        for(int z=0;z<imgsReq.size();z++){

            ProductImageEntity img=ProductImageEntity.builder()
                    .productEntity(productEntity)
                    .publicId(imgsReq.get(z).getPublicId())
                    .imgUrl(imgsReq.get(z).getImgUrl())
                    .isPrimary(z==indexPrimary)
                    .sortOrder( (imgsReq.get(z).getSortOrder()!=null) ? imgsReq.get(z).getSortOrder() : auto++)
                    .build();

            productEntity.getImages().add(img);
        }
    }

    @Override
    public void deleteImageFromProduct(Integer productId){
        Optional<ProductEntity> productEntity = productRepo.findById(productId);
            if(productEntity.isEmpty()) throw new NotFoundException("PRODUCT_NOT_FOUND", "Product not found in database" );

        imageRepository.deleteAllByProductEntity_Id(productId);
        productEntity.get().getImages().clear();
    }

    @Override
    public ProductDto create(UpdateProductReq req) {

        SellerProfileEntity seller = sellerRepo.
                findById(req.getSellerId().intValue())
                .orElseThrow(() -> new NotFoundException("Seller_NOT_FOUND ", "Seller not found in database"));

        CategoryEntity category = categoryRepo.
                findById(req.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category_NOT_FOUND  ", "Category not found in database"));

        if (req.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("INVALID_PRICE", "Price must be greater than zero");
        }

        if (req.getQuantity() <= 0) {
            throw new BadRequestException("INVALID_QUANTITY", "Quantity must be greater than zero");
        }


        ProductEntity  productEntity = ProductEntity.builder()
                .seller(seller)
                .categoryEntity(category)
                .title(req.getTitle())
                .description(req.getDescription())
                .price(req.getPrice())
                .quantity(req.getQuantity())
                .images(new ArrayList<>())
                .build();


    
        addPic(req ,productEntity);

        productRepo.save(productEntity);
        return productMapper.toDto(productEntity);

    }

    @Override
    public ProductDto update(boolean updatePic,Integer id, UpdateProductReq req) {

        Optional<ProductEntity> productEntityOptional = productRepo.findById(id);
        ProductEntity productEntity = productEntityOptional
                .orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND", "Product not found in database"));


        Optional<SellerProfileEntity> sellerProfileEntityOptional= sellerRepo.findById(Math.toIntExact(req.getSellerId()));
        SellerProfileEntity sellerProfileEntity = sellerProfileEntityOptional
                .orElseThrow(() -> new NotFoundException("SELLER_NOT_FOUND", "Seller  not found in database"));


        Optional<CategoryEntity> categoryEntityOptional=categoryRepo.findById(req.getCategoryId());
        CategoryEntity categoryEntity = categoryEntityOptional
                .orElseThrow(() -> new NotFoundException("CATEGORY_NOT_FOUND", "Category  not found in database"));


        productEntity.setSeller(sellerProfileEntity);
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setTitle(req.getTitle());
        productEntity.setDescription(req.getDescription());
        productEntity.setPrice(req.getPrice());
        productEntity.setQuantity(req.getQuantity());
        productEntity.setStatus(req.getStatus() != null ? req.getStatus() : ProductStatus.SOLD_IN);


        if(updatePic) {
            deleteImageFromProduct(id);
            productRepo.flush();
            addPic(req, productEntity);
        }

        return productMapper.toDto(productEntity);
    }

    @Override
    public boolean delete(Integer id) {

        if(!productRepo.existsById(id)){
            throw new NotFoundException("PRODUCT_NOT_FOUND ", "Product not found in database");
        }

        productRepo.deleteById(id);
        return true;
    }
}

