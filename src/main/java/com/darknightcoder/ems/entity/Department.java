package com.darknightcoder.ems.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private long departmentId;
    @Column(name = "department_name", nullable = false)
    private String departmentName;
    @Column(name = "department_type", nullable = false)
    private String departmentType;


}
