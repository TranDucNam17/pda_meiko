package com.example.pda.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
    private static final String TAG = "MAIL_SENDER";
    private static final String SENDER_EMAIL = "pda.system.report@gmail.com"; // Email hệ thống
    private static final String SENDER_PASS  = "your_app_password_here";    // Mật khẩu ứng dụng

    public static void sendFile(String toEmail, String subject, String body, String filePath) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");

                    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASS);
                        }
                    });

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(SENDER_EMAIL));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                    message.setSubject(subject);

                    // Tạo nội dung mail
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setText(body);

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    // Đính kèm file
                    if (filePath != null) {
                        MimeBodyPart attachPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filePath);
                        attachPart.setDataHandler(new DataHandler(source));
                        attachPart.setFileName(new java.io.File(filePath).getName());
                        multipart.addBodyPart(attachPart);
                    }

                    message.setContent(multipart);
                    Transport.send(message);
                    return true;
                } catch (Exception e) {
                    Log.e(TAG, "Lỗi gửi mail: " + e.getMessage());
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Log.i(TAG, "Email log đã được gửi thành công tới " + toEmail);
                }
            }
        }.execute();
    }
}
