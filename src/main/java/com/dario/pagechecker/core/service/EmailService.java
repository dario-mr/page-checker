package com.dario.pagechecker.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static java.lang.String.format;

@Service
@Slf4j
public class EmailService {

    @Resource
    private Session emailSession;

    @Value("${page.url}")
    private String url;

    @Value("${sender.username}")
    private String sender;

    @Value("${recipient.usernames}")
    private String recipients;

    public void send() {
        try {
            Message message = new MimeMessage(emailSession);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipients)
            );
            message.setSubject("Ticket available!");
            message.setText(format("Ticket at %s is available for purchase.", url));

            Transport.send(message);
            log.info("Email sent!");
        } catch (MessagingException ex) {
            throw new RuntimeException(format("Failed to send email from %s to %s", sender, recipients), ex);
        }
    }
}
