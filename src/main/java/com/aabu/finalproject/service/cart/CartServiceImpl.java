package com.aabu.finalproject.service.cart;

import com.aabu.finalproject.config.AuthService;
import com.aabu.finalproject.model.dto.CartDto;
import com.aabu.finalproject.model.entity.CartEntity;
import com.aabu.finalproject.model.entity.UserEntity;
import com.aabu.finalproject.model.mapper.CartMapper;
import com.aabu.finalproject.model.repository.CartRepository;
import com.aabu.finalproject.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final AuthService authService;
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public Long currentUserId() {
        return authService.getCurrentUserId();
    }

    @Override
    public CartDto createCartForCurrentUser() {

        Long currentUserId = currentUserId();

        return cartRepository
                .findTopByUserEntity_IdOrderByCreatedAtDesc(currentUserId)
                .map(cartMapper::toDto)
                .orElseGet(() -> {

                    UserEntity user = userRepository.findById(currentUserId.intValue())
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    CartEntity cart = new CartEntity();
                    cart.setUserEntity(user);

                    CartEntity saved = cartRepository.save(cart);
                    return cartMapper.toDto(saved);
                });
    }


    @Override
    public void deleteLatestCartForCurrentUser() {
        Long currentUserId = currentUserId();
        CartEntity cart =cartRepository.findTopByUserEntity_IdOrderByCreatedAtDesc(currentUserId)
                .orElseThrow(()->new RuntimeException( "No cart Found for this User"));

        cartRepository.delete(cart);
    }

}
