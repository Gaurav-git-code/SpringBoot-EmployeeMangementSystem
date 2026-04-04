package com.darknightcoder.ems.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {
    private List<DepartmentDto> departments;
    private int pageNo;
    private int pageSize;
    private int totalElement;
    private int totalPage;
    private boolean isLast;
}
