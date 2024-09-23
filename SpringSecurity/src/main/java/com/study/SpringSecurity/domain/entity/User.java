package com.study.SpringSecurity.domain.entity;

import com.study.SpringSecurity.security.principal.PrincsipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class User {
    @Id // PK(프라이머리키)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 중복되지 않는 키 값으로 잡음
    private Long id;
    @Column(unique = true, nullable = false)    //nullable = false null을 허용하지않겠다.
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    // fetch: 엔티티를 조인했을 때 연관된 데이터를 언제 가져올지 결정(EAGER - 당장(데이터가 적으면), LAZY - 나중에 사용할 때(데이터가 많으면, 기본값))

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //(외래키)CascadeType.ALL: 나를 참조하고 있는 하위까지 다 삭제
    @JoinTable(
            name = "user_roles",

            // joinColumns가 키 값
            joinColumns = @JoinColumn(name = "user_id"), // user_id없는것 자동으로 만들어줌
            inverseJoinColumns = @JoinColumn(name = "role_id")      // 외래키로 role_id를 잡음
    )
    private Set<Role> roles; // 중복제거 위해서

//    @OneToMany(mappedBy = "user")
//    private Set<UserRole> userRoles = new HashSet<>();

    public PrincsipalUser toPrincipaluser() {
        return PrincsipalUser.builder()
                .userId(id)
                .username(username)
                .password(password)
                .roles(roles)   // 권한
                .build();
    }
}

/**
 *
 * 새 프로젝트 만들기 -> 시큐리티 라이브러리 적용 -> 로그인 ->
 */