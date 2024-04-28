package com.who.shuk.JavaEmail.service.Impl;

import com.who.shuk.JavaEmail.service.EmailService;
import com.who.shuk.JavaEmail.utils.EmailUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.lang.module.Configuration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "emailtemplate";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(from);
            message.setTo(to);
            message.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    @Async
    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            FileSystemResource pic1 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/pic1.jpg"));
            helper.addAttachment(pic1.getFilename(), pic1);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void sendMimeMessageWithEmbeddedImages(String name, String to, String token) {
        //use helper.addInLine();
    }
    @Override
    @Async
    public void sendHtmlEmail(String name, String to, String token) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", name, "url", EmailUtils.getVerificationUrl(host, token)));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(text, true);
            FileSystemResource pic1 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/pic1.jpg"));
            helper.addAttachment(pic1.getFilename(), pic1);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();

    }
}
