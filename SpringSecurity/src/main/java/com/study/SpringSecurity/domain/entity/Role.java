package com.study.SpringSecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;    // ROLE_USER, ROLE_ADMIN, ROLE_MANAGER

    // user 엔티티안에 들어있는 roles
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;    // 중복제거 위해서

//    @OneToMany(mappedBy = "role")
//    private Set<UserRole> userRoles = new HashSet<>();
}
