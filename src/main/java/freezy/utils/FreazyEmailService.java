package freezy.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FreazyEmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String path) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("xyz@gmail.com");
        helper.setText("<html><body><h1>hello Welcome!</h1><body></html>", true);
        FileSystemResource file  = new FileSystemResource(new File(path));
        helper.addAttachment("testfile", file);
        helper.addAttachment("test.png", new ClassPathResource("test.jpeg"));
        helper.setSubject("Hi");
        javaMailSender.send(message);
    }


}
