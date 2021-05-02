package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.service.EmailService;
import com.kisel.Porechanka.util.MyException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "email-simple";
    private static final Logger LOG = Logger.getLogger(EmailServiceImpl.class);

    //@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendMailWithResetToken(String recipientName, String recipientEmail, String resetToken) {
        try {
            final Context ctx = new Context();
            ctx.setVariable("name", recipientName);
            ctx.setVariable("token", resetToken);
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setFrom("immraytal@gmail.com");
            message.setSubject("Advert Project Administration");
            message.setTo(recipientEmail);


            final String htmlContent = this.templateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);
            LOG.info("Sent email with reset token to " + recipientEmail);
        } catch (MessagingException e) {
            LOG.error("Exception in mail service", e);
            throw new MyException("Mail exception in service", e);
        }
    }
}
