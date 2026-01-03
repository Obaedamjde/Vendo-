package com.aabu.finalproject.model.dto.common;

import java.util.List;
import lombok.*;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PageResponse <T>{
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int page; // 0-based
    private int size;
    private boolean first;
    private boolean last;

}
