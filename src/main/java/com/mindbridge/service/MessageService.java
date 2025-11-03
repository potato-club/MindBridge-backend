package com.mindbridge.service;

public interface MessageService {
    void sendAuthCode(String phone);

    boolean verifyAuthCode(String phone, String code);
}
