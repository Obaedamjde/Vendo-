package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.OrderDto;
import com.aabu.finalproject.model.entity.OrderEntity;
import com.aabu.finalproject.model.entity.SellerProfileEntity;
import com.aabu.finalproject.model.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    // DTO -> Entity
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mappings({
            @Mapping(target = "userEntity", expression = "java(toUserEntity(dto.getUserId()))"),
            @Mapping(target = "seller",     expression = "java(toSellerEntity(dto.getSellerId()))"),
            @Mapping(source = "status",     target = "orderStatus"),
            @Mapping(source = "items",      target = "orderItemEntities"),
            @Mapping(target = "buyerNote",  ignore = true)
    })
    OrderEntity toEntity(OrderDto dto);

    // Entity -> DTO
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mappings({
            @Mapping(target = "userId",   expression = "java(entity.getUserEntity() == null ? null : entity.getUserEntity().getId())"),
            @Mapping(target = "sellerId", expression = "java(entity.getSeller() == null ? null : entity.getSeller().getId())"),
            @Mapping(source = "orderStatus",       target = "status"),
            @Mapping(source = "orderItemEntities", target = "items")
    })
    OrderDto toDto(OrderEntity entity);


    List<OrderDto> toDtoList(List<OrderEntity> entities);


    // ===== Helper methods (مهمين جدًا عشان errors اللي عندك)
    default UserEntity toUserEntity(Long id) {
        if (id == null) return null;
        UserEntity u = new UserEntity();
        u.setId(id);
        return u;
    }

    default SellerProfileEntity toSellerEntity(Long id) {
        if (id == null) return null;
        SellerProfileEntity s = new SellerProfileEntity();
        s.setId(id);
        return s;
    }

    // (اختياري لكن مفيد مع OneToMany mappedBy)
    @AfterMapping
    default void linkItems(@MappingTarget OrderEntity entity) {
        if (entity.getOrderItemEntities() == null) return;
        entity.getOrderItemEntities().forEach(oi -> oi.setOrderEntity(entity));
    }
}
