package com.example.eams.external;

import java.util.List;
import java.util.Properties;
import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.util.Log;

public class Gmail {

    final String EMAIL_PORT = "587";// gmail's smtp port
    final String SMTP_AUTH = "true";
    final String START_TLS = "true";
    final String EMAIL_HOST = "smtp.gmail.com";

    String fromEmail;
    String fromPassword;
    List<String> toEmailList;
    String emailSubject;
    String emailBody;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public Gmail() {

    }

    public Gmail(String fromEmail, String fromPassword, List toEmailList, String emailSubject, String emailBody) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", EMAIL_PORT);
        emailProperties.put("mail.smtp.auth", SMTP_AUTH);
        emailProperties.put("mail.smtp.starttls.enable", START_TLS);
        Log.i("Gmail", "Mail server properties set.");
    }

    public MimeMessage createEmailMessage() throws AddressException, MessagingException, UnsupportedEncodingException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        for (String toEmail : toEmailList) {
            Log.i("Gmail","toEmail: "+toEmail);
            emailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");// for a html email
        // emailMessage.setText(emailBody);// for a text email
        Log.i("Gmail", "Email Message created.");
        return emailMessage;
    }

    public void sendEmail() throws MessagingException {

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(EMAIL_HOST, fromEmail, fromPassword);
        Log.i("Gmail","allrecipients: "+emailMessage.getAllRecipients());
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i("Gmail", "Email sent successfully.");
    }

}