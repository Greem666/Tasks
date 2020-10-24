package com.crud.tasks.service;

import com.crud.tasks.domain.mail.Mail;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    public static final String NEW_CARD_EMAIL = "New card email";
    public static final String ONCE_A_DAY_EMAIL = "Once a day email";

    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

    public void send(final Mail mail, String mailType) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail, mailType));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    protected MimeMessagePreparator createMimeMessage(final Mail mail, String emailType) {

        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mail.getMailTo());
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(messageFormatFactory(emailType, mail), true);
            if (mail.getToCc() != null) {
                messageHelper.setCc(mail.getToCc());
            }
        };
    }

    private String messageFormatFactory(String emailType, Mail mail) {
        switch(emailType) {
            case NEW_CARD_EMAIL: default:
                return mailCreatorService.buildTrelloCardEmail(mail.getMessage());
            case ONCE_A_DAY_EMAIL:
                return mailCreatorService.buildOnceADayEmail(mail.getMessage());
        }
    }

    public SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Needed on my PC, as it is not configured to have a fully qualified domain name
        mailMessage.setFrom(mail.getMailTo());

        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        if (mail.getToCc() != null) {
            mailMessage.setCc(mail.getToCc());
        }

        return mailMessage;
    }
}
