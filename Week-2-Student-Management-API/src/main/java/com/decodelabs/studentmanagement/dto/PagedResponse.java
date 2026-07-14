package com.decodelabs.studentmanagement.dto;

import lombok.*;

import java.util.List;

/**
 * Generic response wrapper for paginated data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
