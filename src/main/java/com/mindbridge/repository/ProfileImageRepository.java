package com.mindbridge.repository;

import com.mindbridge.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {
    ProfileImageEntity findFirstUrlByUserIdOrderByRecordedAtDesc(long userId);

    @Modifying
    @Query("DELETE FROM ProfileImageEntity b WHERE b.url = :imageUrl")
    @Transactional
    void deleteByUrl(String imageUrl);

    List<ProfileImageEntity> findByUserIdOrderByRecordedAtDesc(long userId);
}