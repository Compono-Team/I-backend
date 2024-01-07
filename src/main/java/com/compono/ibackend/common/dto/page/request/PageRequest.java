package com.compono.ibackend.common.dto.page.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    private Integer page;
    private Integer rows;
    private String criteria;

    public static PageRequest of(int page, int rows, String criteria) {
        return new PageRequest(page, rows, criteria);
    }

    public static PageRequest of(int page, int rows) {
        return PageRequest.of(page, rows, "");
    }

    public Integer getPage() {
        if (page == null) {
            return 0;
        }
        return page;
    }

    public Integer getRows() {
        if (rows == null) {
            return 10;
        }
        return rows;
    }

    public String getCriteria() {
        if (criteria == null) {
            return "createdAt";
        }
        return criteria;
    }

    public Integer getOffset() {
        return (getPage() - 1) * getRows();
    }

    public org.springframework.data.domain.PageRequest getPageRequest() {
        return org.springframework.data.domain.PageRequest.of(
                getPage(), getRows(), Sort.by(Sort.Direction.DESC, getCriteria()));
    }
}
