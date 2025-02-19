package com.hello_doctor_api.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;@Service
public class OtpConfig {

    @Value("${otp.expire.time}")
    private int EXPIRY_TIME;
    @Value("${otp.length}")
    private  int length;

    private final Map<String, Integer> otpStore = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();



    public int generateOtp(String email) throws MessagingException, UnsupportedEncodingException {
        int otp = 100000 + secureRandom.nextInt(900000);
        otpStore.put(email, otp);

        Executors.newSingleThreadScheduledExecutor().schedule(
                () -> otpStore.remove(email), EXPIRY_TIME, TimeUnit.SECONDS
        );

        sendEmail(email, otp);
        return otp;
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("testmails596@gmail.com");
        mailSender.setPassword("axbs sanp tugt hifx");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }


    private void sendEmail(String email, int otp) throws MessagingException, UnsupportedEncodingException {
        JavaMailSender javaMailSender = getJavaMailSender();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("testmails596@gmail.com", "Hello Doctor Support");
        helper.setTo(email);
        String subject = "otp";
        String content ="your OTP code is: " + otp + "\nIt is valid for 60 seconds" ;
        helper.setSubject(subject);
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
