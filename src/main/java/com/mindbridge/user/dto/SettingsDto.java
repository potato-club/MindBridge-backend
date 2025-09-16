package com.mindbridge.user.dto;

public record SettingsDto(
        Boolean pushEnabled,
        Boolean emailEnabled,
        Boolean privateProfile,
        String language
) {}
