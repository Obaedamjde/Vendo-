package com.aabu.finalproject.service.cartItem;

import com.aabu.finalproject.config.AuthService;
import com.aabu.finalproject.model.dto.CartDto;
import com.aabu.finalproject.model.dto.CartItemDto;
import com.aabu.finalproject.model.dto.request.CartItemReq;
import com.aabu.finalproject.model.dto.updateReq.UpdateCartItemReq;
import com.aabu.finalproject.model.entity.CartEntity;
import com.aabu.finalproject.model.entity.CartItemEntity;
import com.aabu.finalproject.model.entity.ProductEntity;
import com.aabu.finalproject.model.mapper.CartItemMapper;
import com.aabu.finalproject.model.mapper.CartMapper;
import com.aabu.finalproject.model.repository.CartItemRepository;
import com.aabu.finalproject.model.repository.CartRepository;
import com.aabu.finalproject.model.repository.ProductRepository;
import com.aabu.finalproject.service.cart.CartServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartItemImpl implements CartItemService{

    private final CartServiceImpl cartService;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemDto addItem(CartItemReq req) {

        Long userId = cartService.currentUserId();

        CartEntity cartEntity = cartRepository.findTopByUserEntity_IdOrderByCreatedAtDesc(userId)
                .orElseGet(() -> cartRepository.save(
                        cartMapper.toEntity(cartService.createCartForCurrentUser())
                ));

        ProductEntity productEntity = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + req.getProductId()));

        int qtyToAdd = (req.getQuantity() == 0 ? 1 : req.getQuantity());

        //if item exist
        CartItemEntity existingItem = cartEntity.getCartItemEntities().stream()
                .filter(ci -> ci.getProductEntity() != null
                        && ci.getProductEntity().getId().equals(productEntity.getId()))
                .findFirst()
                .orElse(null);

        int availableStock = productEntity.getQuantity(); // أو quantityAvailable

        int currentQtyInCart = existingItem != null ? existingItem.getQuantity() : 0;

        if (currentQtyInCart + qtyToAdd > availableStock) {
            throw new RuntimeException(
                    "Requested quantity exceeds available stock. Available: " + availableStock
            );
        }


        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + qtyToAdd);
            cartRepository.save(cartEntity);
            return cartItemMapper.toDto(existingItem);
        }

        CartItemEntity newItem = CartItemEntity.builder()
                .cartEntity(cartEntity)
                .productEntity(productEntity)
                .quantity(qtyToAdd)
                .unitPrice(productEntity.getPrice())
                .build();

        cartEntity.getCartItemEntities().add(newItem);
        cartRepository.save(cartEntity);

        return cartItemMapper.toDto(newItem);
    }

    public CartItemDto updateQuantity(UpdateCartItemReq req) {

        Long userId = cartService.currentUserId();

        CartItemEntity item = cartItemRepository
                .findByIdAndCartEntity_UserEntity_Id(req.getCartItemId(), userId)
                .orElseThrow(() -> new RuntimeException("Cart item not found in your cart"));

        int availableStock = item.getProductEntity().getQuantity();

        if (req.getQuantity() > availableStock) {
            throw new RuntimeException("Requested quantity exceeds available stock. Available: " + availableStock);
        }

        if (req.getQuantity() == 0) {
            CartEntity cart = item.getCartEntity();
            cart.getCartItemEntities().remove(item);
            cartItemRepository.delete(item);
            return null; // أو رجّع CartDto
        }

        item.setQuantity(req.getQuantity());
        cartItemRepository.save(item);

        return cartItemMapper.toDto(item);
    }


    @Override
    public void deleteItem(Long itemId) {
        cartItemRepository.deleteById(itemId.intValue());

    }
}
