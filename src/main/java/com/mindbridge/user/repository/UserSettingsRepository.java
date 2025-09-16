package com.mindbridge.user.repository;

import com.mindbridge.user.domain.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> { }
