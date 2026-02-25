package com.mindbridge.repository;

import com.mindbridge.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);

    // 사용자 이름 조회
    @Query("SELECT u.username " +
            "FROM UserEntity u " +
            "WHERE u.id = :userId")
    String findNameById(@Param("userId") Long userId);

}
