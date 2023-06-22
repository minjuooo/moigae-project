package com.moigae.application.component.user.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MailSendServiceTest {
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private MimeMessage mimeMessage;
    private MailSendService mailSendService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        mailSendService = new MailSendService(mailSender);
    }

    @Test
    public void testJoinEmail() {
        //given
        String email = "test@gmail.com";

        //when
        doNothing().when(mailSender).send(any(MimeMessage.class));
        String result = mailSendService.joinEmail(email);

        //then
        assertThat(result).isNotEmpty();
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testMailSend() {
        //given
        String setFrom = "moigae222@gmail.com";
        String toMail = "test@gmail.com";
        String title = "test title";
        String content = "test content";

        //when
        doNothing().when(mailSender).send(any(MimeMessage.class));
        mailSendService.mailSend(setFrom, toMail, title, content);

        //then
        verify(mailSender).send(any(MimeMessage.class));
    }
}