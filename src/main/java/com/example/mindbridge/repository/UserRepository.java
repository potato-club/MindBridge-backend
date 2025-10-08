package com.example.mindbridge.repository;

import com.example.mindbridge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);
}
