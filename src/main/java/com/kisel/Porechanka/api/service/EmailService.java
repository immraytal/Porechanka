package com.kisel.Porechanka.api.service;

public interface EmailService {
    void sendMailWithResetToken(String recipientName, String recipientEmail, String resetToken);
}
