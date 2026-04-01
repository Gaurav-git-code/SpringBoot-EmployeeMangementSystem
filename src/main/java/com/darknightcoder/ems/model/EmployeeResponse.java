package com.darknightcoder.ems.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private List<EmployeeDto> content;
    private int pageNo;
    private int pageSize;
    private int totalElement;
    private int totalPage;
    private boolean isLastPage;
}
