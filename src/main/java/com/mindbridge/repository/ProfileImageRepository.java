package com.mindbridge.repository;

import com.mindbridge.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {
    ProfileImageEntity findFirstUrlByUserIdOrderByRecordedAtDesc(long userId);

    Optional<ProfileImageEntity> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM ProfileImageEntity b WHERE b.id = :imageId")
    @Transactional
    void deleteByUrl(Long imageId);

    List<ProfileImageEntity> findByUserIdOrderByRecordedAtDesc(long userId);
}