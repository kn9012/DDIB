package com.ddib.notification.notification.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    private final String SUBJECT = "[DDIB] 새로운 타임딜 상품 등록 안내";

    public boolean sendWillLink(String email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String htmlContent = getWillMessage();

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException exception) {
            log.error(exception.getMessage());
            return false;
        }
        return true;
    }

    private String getWillMessage() {
        String certificationMessage = "";
        certificationMessage += "<h1 style='text-align: center;'>[DDIB] 새로운 타임딜 상품 등록 안내입니다.</h1>";
        certificationMessage += "<h3 style='text-align: center;'>새 타임딜 상품이 등록되었습니다.</h3><br><br>";
        return certificationMessage;
    }
}