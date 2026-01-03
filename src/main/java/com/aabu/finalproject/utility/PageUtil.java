package com.aabu.finalproject.utility;

import com.aabu.finalproject.model.dto.common.PageResponse;
import com.aabu.finalproject.model.dto.common.PageReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;



public final class  PageUtil {

    private PageUtil(){} // to avoid make a new instance


    public static <E,D> PageResponse<D> toPageResponse(Page<E> page , Function<E,D> mapper){
        List<D> data= page.getContent().stream().map(mapper).toList();

        return PageResponse.<D>builder()
                .content(data)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .page(page.getNumber())
                .size(page.getSize())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }


    public static Pageable toPageable (PageReqDTO pageReqDTO){
        return PageRequest.of(pageReqDTO.getPage(),pageReqDTO.getElement() , parseSort( pageReqDTO.getSort() ));
    }

    public static Pageable toPageableNoSort(PageReqDTO pageReqDTO){
        return PageRequest.of(pageReqDTO.getPage(), pageReqDTO.getElement(), Sort.unsorted());
    }

    //
    public static Sort parseSort(String sortString) {
        if (sortString == null || sortString.isBlank() || sortString.isEmpty()) return Sort.unsorted();


        // 1 ) "name,asc; 2 ) id,desc"
        String[] parts = sortString.split(";");
        List<Sort.Order> orders = new ArrayList<>();


        // 1 ) "name,asc; 2 ) id,desc"
        for (String sub : parts) {
            String[] field_direction = sub.trim().split(",");
            String field = field_direction[0].trim();
            Sort.Direction direction = (field_direction.length > 1 && "asc".equalsIgnoreCase(field_direction[1].trim())
                    ? Sort.Direction.ASC : Sort.Direction.DESC);

            orders.add(new Sort.Order(direction,field));
        }

        return Sort.by(orders);

    }
}
