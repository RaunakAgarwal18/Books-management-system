package com.raunak.library.book_service.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class AppUtil {
    public static Pageable createPageable(Integer pageNumber, Integer pageSize) {
        int safePage = pageNumber == null ? 0 : Math.max(pageNumber, 0);
        int safeSize = pageSize == null ? 5 :Math.min(Math.max(pageSize, 1), 5);
        return PageRequest.of(safePage, safeSize);
    }
}
