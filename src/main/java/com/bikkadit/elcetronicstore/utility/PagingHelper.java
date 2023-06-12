package com.bikkadit.elcetronicstore.utility;

import com.bikkadit.elcetronicstore.payloads.PageResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PagingHelper {

    public static <U, V> PageResponse<V> getPageResponse(Page<U> page, Class<V> type) {

        List<U> entity = page.getContent();

        List<V> userList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageResponse<V> response = new PageResponse<>();
        response.setContent(userList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }


}