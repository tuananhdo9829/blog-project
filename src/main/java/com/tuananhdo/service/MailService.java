package com.tuananhdo.service;

import com.tuananhdo.dto.RegistrationDTO;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {

    void sendMailForgotPassword(String email, String resetPasswordToken) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationMail(RegistrationDTO registrationDTO, String verifyToken);
}
