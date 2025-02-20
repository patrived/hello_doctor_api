package com.hello_doctor_api.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class OtpConfig {

    @Value("${otp.expire.time}")
    private int EXPIRY_TIME;
    @Value("${otp.length}")
    private int length;

    private final Map<String, Integer> otpStore = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String mail;

    public int generateOtp(String email) throws MessagingException, UnsupportedEncodingException {
        int otp = 100000 + secureRandom.nextInt(900000);
        otpStore.put(email, otp);

        // Schedule OTP expiration
        Executors.newSingleThreadScheduledExecutor().schedule(
                () -> otpStore.remove(email), EXPIRY_TIME, TimeUnit.SECONDS
        );

        sendEmail(email, otp);
        return otp;
    }

    private void sendEmail(String email, int otp) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(mail, "Hello Doctor Support");
        helper.setTo(email);
        helper.setSubject("OTP Verification");
        String content = "Your OTP code is: " + otp + "\nIt is valid for " + EXPIRY_TIME + " seconds.";
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    public boolean validateOtp(String email, int otp) {
        Integer storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp == otp) {
            otpStore.remove(email);
            return true;
        }
        return false;
    }
}
