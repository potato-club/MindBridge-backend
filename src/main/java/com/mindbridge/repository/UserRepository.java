package com.mindbridge.repository;

import com.mindbridge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUserid(String userid);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<UserEntity> findByUsernameAndPhoneNumber(String username, String phoneNumber);
    Optional<UserEntity> findByUseridAndPhoneNumber(String userid, String phoneNumber);

    Optional<UserEntity> findByUserid(String userid);
}
