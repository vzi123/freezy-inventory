package freezy.utils;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class FreazyEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    Logger logger;
    public void sendEmail(File attachment){
        try{

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(new InternetAddress("no-reply@tripor.ai"));
            helper.setTo(new InternetAddress("vedaprakash.n@gmail.com"));
            helper.setSubject("Quotation");
            helper.setText(generateCommonHtmlHead().toString(), true);

            helper.addAttachment(attachment.getName(), attachment);
            javaMailSender.send(message);

        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private StringBuilder generateCommonHtmlHead() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("<head>Hi Admin</head>")
                .append("<p>There is a new Quotation Created. Please find the details in the attachment.</p>")
                .append("<p>Thanks</p>\\n\\ <p>Freazy Admin</p>");
    }


}
