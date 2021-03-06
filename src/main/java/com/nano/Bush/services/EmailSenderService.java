package com.nano.Bush.services;

/**
 * Created by Matias Zeitune oct. 2019
 **/

import com.nano.Bush.datasources.UsersDao;
import com.sun.mail.smtp.SMTPTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

@Service
public class EmailSenderService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private Base64ToPdfDecoder base64ToPdfDecoder;

    public void sendEmail(String subject, Optional<String> base64pdf, String html, String user, Optional<String> mail) throws AddressException {
        String EMAIL_TEXT = html;

        final String username = "5nano.consultas@gmail.com";
        final String password = "ebxnjrpslhjtiobb";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            String personMail;
            if(mail.isPresent()){
               personMail = mail.get();
            }else{
                personMail = usersDao.getUserByUsername(user).get().getEmail();
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("5nano.consultas@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(personMail)
            );
            message.setSubject(subject);


            // HTML email
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));
            Multipart mp = new MimeMultipart();
            // file
            if(base64pdf.isPresent()){
                MimeBodyPart attachment = new MimeBodyPart();
                attachment.setDataHandler(base64ToPdfDecoder.decode(base64pdf.get()));
                attachment.setFileName(subject);
                mp.addBodyPart(attachment);
            }

            mp.addBodyPart(htmlPart);

            message.setContent(mp);

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect("smtp.gmail.com", "5nano.consultas@gmail.com", "ebxnjrpslhjtiobb");

            // send
            t.sendMessage(message, message.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (SendFailedException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
        static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }

}