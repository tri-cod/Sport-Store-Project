/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {

    // 🔽 THAY THÔNG TIN CỦA BẠN VÀO ĐÂY 🔽
    private static final String FROM_EMAIL = "btkhanh123@gmail.com"; 
    private static final String APP_PASSWORD = "wihpddlsprmvqcxs"; 

    public static boolean sendResetPasswordEmail(String toEmail, String resetLink) {
        
        // 1. Cấu hình
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // 2. Tạo Session (phiên làm việc)
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        // 3. Soạn email
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM_EMAIL));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            
            // Tiêu đề và nội dung
            msg.setSubject("[SportStore] Yeu Cau Dat Lai Mat Khau", "UTF-8");
            
            String emailContent = "<h2>Yeu cau dat lai mat khau cho SportStore</h2>"
                    + "<p>Ban da yeu cau dat lai mat khau. Vui long nhan vao link duoi day de tiep tuc:</p>"
                    + "<p><a href='" + resetLink + "'>DAT LAI MAT KHAU</a></p>"
                    + "<p>Link nay se het han sau 1 phut.</p>"
                    + "<p>Neu ban khong yeu cau, vui long bo qua email nay.</p>";
            
            msg.setContent(emailContent, "text/html; charset=UTF-8");

            // 4. Gửi email
            Transport.send(msg);
            
            System.out.println("✅ Email gui den " + toEmail + " thanh cong!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Gui email that bai: " + e.getMessage());
            return false;
        }
    }
}