package com.mindbridge.repository;

import com.mindbridge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserid(String userid);
    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    boolean existsByUserId(String userid);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);
}
