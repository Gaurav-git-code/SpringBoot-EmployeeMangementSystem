package com.darknightcoder.ems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ems_role", uniqueConstraints = {
        @UniqueConstraint(columnNames = "role_type")
})
public class EmsRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_type",nullable = false)
    private String roleType;
    @ManyToMany(mappedBy = "roles")
    private Set<EmsUser> users;
}
