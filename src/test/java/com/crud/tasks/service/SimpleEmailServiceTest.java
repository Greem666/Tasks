package com.crud.tasks.service;

import com.crud.tasks.domain.mail.Mail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendNewCardEmail() {
        // Given
        Mail mail = new Mail("test@test.com", "Test", "Test message", "test2@test.com");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());
        mailMessage.setFrom(mail.getMailTo());

        // When
        simpleEmailService.send(mail, SimpleEmailService.NEW_CARD_EMAIL);

        // Then
        ArgumentCaptor<MimeMessagePreparator> argumentCaptor = ArgumentCaptor.forClass(MimeMessagePreparator.class);
        verify(javaMailSender, times(1)).send(argumentCaptor.capture());

    }

    @Test
    public void shouldSendOnceADayEmail() {
        // Given
        Mail mail = new Mail("test@test.com", "Test", "Test message", "test2@test.com");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());
        mailMessage.setFrom(mail.getMailTo());

        // When
        simpleEmailService.send(mail, SimpleEmailService.ONCE_A_DAY_EMAIL);

        // Then
        ArgumentCaptor<MimeMessagePreparator> argumentCaptor = ArgumentCaptor.forClass(MimeMessagePreparator.class);
        verify(javaMailSender, times(1)).send(argumentCaptor.capture());

    }

    @Test
    public void shouldSetCc() {
        // Given
        Mail mail = new Mail("test@test.com", "Test", "Test message", "test_CC@test.com");
//        SimpleEmailService simpleEmailService = new SimpleEmailService();

        // When
        SimpleMailMessage mailMessage = simpleEmailService.createMailMessage(mail);

        // Then
        Assert.assertEquals("test_CC@test.com", mailMessage.getCc()[0]);
    }

}