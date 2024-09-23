package com.study.SpringSecurity.repository;

import com.study.SpringSecurity.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository                         // Long : User안의 기본키 자료형
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}