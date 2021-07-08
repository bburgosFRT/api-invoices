package com.frt.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.frt.model.Invoice;

public class Mail {
    
    public static void sendMail(Invoice invoice, ByteArrayOutputStream outputStream) throws Exception {

        final String username = "powerbi@flowingriverstech.com";
        final String password = "Hg$oT8379*af$";
        final String content = "Adjunto sírvase encontrar su documento tributario electrónico número " + invoice.firma + ".pdf certificada por la Superintendencia de Administración Tributaria (SAT)\n"
                + "\n" + "Este envío además de facilitarle su comprobante fiscal de un modo rápido, cómodo y seguro, constituye en promover la adecuada utilización de los recursos naturales para un futuro de nuestro planeta.\n"
                + "\n" + "Lo invitamos a unirse a nuestros esfuerzos no imprimiendo este correo, si no es estrictamente necesario. Adjunto sírvase encontrar su documento tributario electrónico número " + invoice.firma + ".pdf certificada por la Superintendencia de Administración Tributaria (SAT)\n"
                + "\n " + "Este envío además de facilitarle su comprobante fiscal de un modo rápido, cómodo y seguro, constituye en promover la adecuada utilización de los recursos naturales para un futuro de nuestro planeta.\n"
                + "\n " + "Lo invitamos a unirse a nuestros esfuerzos no imprimiendo este correo, si no es estrictamente necesario."; //this will be the text of the email
        final String subject = "DOCUMENTO TRIBUTARIO ELECTRONICO 1974590180"; //this will be the subject of the email  

        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS   
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        try {

            Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });

            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            byte[] bytes = outputStream.toByteArray();

            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName(invoice.firma + ".pdf");

            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(username);
            InternetAddress iaRecipient = new InternetAddress(invoice.correo);

            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);

            //send off the email
            Transport.send(mimeMessage);

            System.out.println("Correo enviado");

        } catch (MessagingException ex) {
            throw ex;
        } finally {
            //clean off
            if (null != outputStream) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        //Message message = prepareMessage(session, username, recepient);
        //Transport.send(message);
    }

    public static void sendMail(String correo,ByteArrayOutputStream outputStream) throws Exception {

        final String username = "powerbi@flowingriverstech.com";
        final String password = "Hg$oT8379*af$";
        final String content = "Adjunto sírvase encontrar nota de debito generada por el Banco XXX."; //this will be the text of the email
        final String subject = "Notta de Debito"; //this will be the subject of the email  

        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS   
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        try {

            Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });

            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            byte[] bytes = outputStream.toByteArray();

            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("nota.pdf");

            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(username);
            InternetAddress iaRecipient = new InternetAddress(correo);

            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);

            //send off the email
            Transport.send(mimeMessage);

            System.out.println("Correo enviado");

        } catch (MessagingException ex) {
            throw ex;
        } finally {
            //clean off
            if (null != outputStream) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        //Message message = prepareMessage(session, username, recepient);
        //Transport.send(message);
    }

}
