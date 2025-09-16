package com.mindbridge.user.repository;

import com.mindbridge.user.domain.Reaction;
import com.mindbridge.user.domain.ReactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Page<Reaction> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Page<Reaction> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, ReactionType type, Pageable pageable);
}
