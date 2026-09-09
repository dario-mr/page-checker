package com.dario.pagechecker.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final Session emailSession;

    @Value("${sender.address}")
    private String sender;

    @Value("${recipient.addresses}")
    private String recipients;

    public void send(String subject, String body) {
        log.info("Sending email...");

        try {
            var message = new MimeMessage(emailSession);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipients)
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            log.info("Email sent!");
        } catch (Exception ex) {
            throw new RuntimeException(format("Failed to send email from [%s] to [%s]", sender, recipients), ex);
        }
    }
}
