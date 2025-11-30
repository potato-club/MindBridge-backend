package com.mindbridge.repository;

import com.mindbridge.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);
}
