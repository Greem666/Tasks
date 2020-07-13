package com.crud.tasks.service;

import com.crud.tasks.domain.mail.Mail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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
    public void shouldSendEmail() {
        // Given
        Mail mail = new Mail("test@test.com", "Test", "Test message");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());

        // When
        simpleEmailService.send(mail);

        // Then
        verify(javaMailSender, times(1)).send(mailMessage);

    }

    @Test
    public void shouldNotSetAnyCc() {
        // Given
        Mail mail = new Mail("test@test.com", "Test", "Test message");
        SimpleEmailService simpleEmailService = new SimpleEmailService();

        // When
        SimpleMailMessage mailMessage = simpleEmailService.createMailMessage(mail);

        // Then
        String[] expectedCcValue = new String[] {null};
        assertArrayEquals(expectedCcValue, mailMessage.getCc());
    }

    @Test
    public void shouldSetCc() {
        // Given
        Mail mail = new Mail("test@test.com", "Test", "Test message", "test_CC@test.com");
        SimpleEmailService simpleEmailService = new SimpleEmailService();

        // When
        SimpleMailMessage mailMessage = simpleEmailService.createMailMessage(mail);

        // Then
        Assert.assertEquals("test_CC@test.com", mailMessage.getCc()[0]);
    }

}