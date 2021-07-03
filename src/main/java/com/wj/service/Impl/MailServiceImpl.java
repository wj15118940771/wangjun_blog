package com.wj.service.Impl;

import com.wj.service.MailService;
import com.wj.utils.Static;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private JavaMailSender mailSender;

    private static final String SENDER = "wj15118940771@163.com";



    @Override
    public void sendMimeMessge(String to, String subject,String code) {
        MimeMessage message = mailSender.createMimeMessage();

        String mail="<html><head></head><body><h1>这是一封激活邮件，点以下链接注册账户,有效时间为5分钟(若不是本人操作，可忽略该条邮件,无法点击请复制链接到浏览器)</h1><h3><a href='" +
                Static.email +
                "/email/active?code=" +
                code +
                "'>" +
                Static.email +
                "/email/active?code=" +code+
                "</a></h3></body></html>";
        try {

            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(mail, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送MimeMessge时发生异常！", e);
        }

    }

    @Override
    public void sendResetpwMessge(String to, String subject,String code) {
        MimeMessage message = mailSender.createMimeMessage();

        String mail="<html><head></head><body><h1>这是一封重置密码邮件，点以下链接重置密码(若不是本人操作，可忽略该条邮件,无法点击请复制链接到浏览器)</h1><h3><a href='" +
                Static.email +
                "/resetpw/active?code=" +
                code +
                "'>" +
                Static.email +
                "/resetpw/active?code=" +code+
                "</a></h3></body></html>";
        try {

            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(mail, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送MimeMessge时发生异常！", e);
        }

    }
}
