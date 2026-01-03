package com.aabu.finalproject.service.order;

import com.aabu.finalproject.config.AuthService;
import com.aabu.finalproject.model.dto.OrderDto;
import com.aabu.finalproject.model.entity.*;
import com.aabu.finalproject.model.enums.OrderStatus;
import com.aabu.finalproject.model.mapper.OrderMapper;
import com.aabu.finalproject.model.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final AuthService authService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;


    @Transactional
    @Override
    public OrderDto createOrder() {

        Long userId = authService.getCurrentUserId();
        CartEntity cartEntity = cartRepository.getByUserEntity_Id(userId); // cart id
        if(cartEntity==null){
            throw new RuntimeException("this user has not have cart ");
        }

        List<CartItemEntity> cartItems= cartItemRepository.getAllByCartEntity_Id(cartEntity.getId());
        List<Integer>productIds= cartItems.stream()
                .map(ci->ci.getProductEntity().getId())
                .toList();


        //to get seller id
        Optional<ProductEntity> productEntity=productRepository.findById(cartItems.get(0).getProductEntity().getId());
        Integer sellerId= productEntity.get().getSeller().getId().intValue();


        if(productRepository.countByIdInAndSeller_IdNot(productIds, sellerId.longValue()) >0){
            throw new RuntimeException("All Product must related to one seller ");
        }

        Map<Integer,ProductEntity> productMap =productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap( ProductEntity::getId ,p->p  ));


        OrderDto orderDto=OrderDto.builder()
                .userId(userId)
                .sellerId(sellerId.longValue())
                .status(OrderStatus.PENDING)
                .subTotal(BigDecimal.ZERO)
                .build();



        OrderEntity orderEntity= orderMapper.toEntity(orderDto);
        orderRepository.save(orderEntity);

        BigDecimal subTotal = BigDecimal.ZERO;

        List<OrderItemEntity> listOrderItemEntities =new ArrayList<>();
        for(CartItemEntity ci : cartItems){

            ProductEntity entity=productMap.get(ci.getProductEntity().getId());
            BigDecimal unitPrice=ci.getUnitPrice();
            BigDecimal totalPriceOfProduct= unitPrice.multiply(BigDecimal.valueOf(ci.getQuantity()));

            OrderItemEntity orderItemEntity= OrderItemEntity.builder()
                    .orderEntity( orderEntity )
                    .productEntity(entity)
                    .quantity(ci.getQuantity())
                    .unitPrice(entity.getPrice())
                    .build();

            listOrderItemEntities.add(orderItemEntity);
            subTotal=subTotal.add(totalPriceOfProduct);
        }

        orderItemRepository.saveAll(listOrderItemEntities);
        orderEntity.setSubTotal(subTotal);
        orderRepository.save(orderEntity);

        return orderMapper.toDto(orderEntity);

    }

    @Override
    public OrderDto confirmOrder() {

        Long userId = authService.getCurrentUserId();
        OrderEntity orderEntity= orderRepository.getByUserEntity_Id(userId);

        if (orderEntity == null) throw new RuntimeException("Order not found");

        List<OrderItemEntity> orderItems= orderItemRepository.getAllByOrderEntity_Id(orderEntity.getId());
        Map<Integer,Integer> mapProductIdAndQuantity= orderItems
                .stream().collect(Collectors.toMap( oi->oi.getProductEntity().getId(),
                        OrderItemEntity::getQuantity,
                        Integer::sum));

        for(Map.Entry<Integer,Integer> entry :mapProductIdAndQuantity.entrySet()){
            Integer productId=entry.getKey();
            Integer quantity = entry.getValue();

            int updated = productRepository.decrementStockIfAvailable(productId,quantity);
            if(updated==0){
                throw new RuntimeException("Insufficient stock for productId=" + entry.getKey());
            }
        }


        orderEntity.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(orderEntity);

        return orderMapper.toDto(orderEntity);
    }

    @Override
    public void cancelOrder() {

        Long userId = authService.getCurrentUserId();
        OrderEntity orderEntity= orderRepository.getByUserEntity_Id(userId);
        if (orderEntity == null) throw new RuntimeException("Order not found");


        List<OrderItemEntity> orderItems= orderItemRepository.getAllByOrderEntity_Id(orderEntity.getId());
        Map<Integer,Integer> mapProductIdAndQuantity= orderItems
                .stream().collect(Collectors.toMap( oi->oi.getProductEntity().getId(),
                        OrderItemEntity::getQuantity,
                        Integer::sum));


        for(Map.Entry<Integer,Integer> entry :mapProductIdAndQuantity.entrySet()){
            Integer productId=entry.getKey();
            Integer quantity = entry.getValue();

            int updated = productRepository.incrementStockAfterCancel(productId,quantity);
            if(updated==0){
                throw new RuntimeException("Sorry, Cant Cancel order");
            }
        }

        orderRepository.deleteByIdAndUserEntity_Id(orderEntity.getId() , userId);


    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }
}
