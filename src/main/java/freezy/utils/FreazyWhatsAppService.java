package freezy.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

@Slf4j
@Service
public class FreazyWhatsAppService {

    Logger logger;

    public Object sendMessage(String phoneNumber, String message, String templateId) {
        try {
            if (isPhoneNumberValid(phoneNumber)) {
                Twilio.init("AC8903c55131234b42768cc0f4c60360b2", "e1727ae514209c01e6307905a3bddae9");
                PhoneNumber to = new PhoneNumber("whatsapp:"+phoneNumber);
                PhoneNumber from = new PhoneNumber("whatsapp:+14052679902");//("MG8e1c09441f02353105e00fc419b735f4");
                System.out.println("Message : " + message);
                System.out.println("Template : " + templateId);
                System.out.println("From : " + from.getEndpoint());
                System.out.println("To : " + to.getEndpoint());
                Message twilioMessage = Message.creator(
                                to,
                                from,"body")
                        .setContentVariables(message)
                        .setContentSid(templateId)
                        .setMessagingServiceSid("MG8e1c09441f02353105e00fc419b735f4")
                        .create();
                System.out.println("Message sent with SID: " + twilioMessage.getSid());
            } else {
                throw new IllegalArgumentException(
                        "Phone number [" + phoneNumber + "] is not a valid number"
                );
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }

    public Object sendMessageWithAttachment(String phoneNumber, String message, String templateId, String fileURL) {
        try {
            if (isPhoneNumberValid(phoneNumber)) {
                Twilio.init("AC8903c55131234b42768cc0f4c60360b2", "e1727ae514209c01e6307905a3bddae9");
                PhoneNumber to = new PhoneNumber("whatsapp:"+phoneNumber);
                PhoneNumber from = new PhoneNumber("whatsapp:+14052679902");//("MG8e1c09441f02353105e00fc419b735f4");
                System.out.println("Message : " + message);
                System.out.println("Template : " + templateId);
                System.out.println("From : " + from.getEndpoint());
                System.out.println("To : " + to.getEndpoint());
                Message twilioMessage = Message.creator(
                                to,
                                from,"body")
                        .setContentVariables(message)
                        .setContentSid(templateId)
                        .setMediaUrl("https://drive.google.com/file/d/15hMcaa3WWJMw_lKzaGXifkIhDGRe2alU/view")
                        .setMessagingServiceSid("MG8e1c09441f02353105e00fc419b735f4")
                        .create();
                System.out.println("Message sent with SID: " + twilioMessage.getSid());
            } else {
                throw new IllegalArgumentException(
                        "Phone number [" + phoneNumber + "] is not a valid number"
                );
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }

    public Object sendFile(String phoneNumber, String message, String templateId, String fileURL) {
        try {
            if (isPhoneNumberValid(phoneNumber)) {
                Twilio.init("AC8903c55131234b42768cc0f4c60360b2", "e1727ae514209c01e6307905a3bddae9");
                PhoneNumber to = new PhoneNumber("whatsapp:"+phoneNumber);
                PhoneNumber from = new PhoneNumber("whatsapp:+14052679902");//("MG8e1c09441f02353105e00fc419b735f4");
                System.out.println("Message : " + message);
                System.out.println("Template : " + templateId);
                System.out.println("From : " + from.getEndpoint());
                System.out.println("To : " + to.getEndpoint());
                Message twilioMessage = Message.creator(
                                to,
                                from,
                                "Here is the generated DC for a customer.")
                        .setMessagingServiceSid("MG8e1c09441f02353105e00fc419b735f4")
                        .setMediaUrl(Arrays.asList(URI.create(fileURL)))
                        .create();
                System.out.println("Message sent with SID: " + twilioMessage.getSid());
                System.out.println("Message status: " + twilioMessage.getStatus());
            } else {
                throw new IllegalArgumentException(
                        "Phone number [" + phoneNumber + "] is not a valid number"
                );
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }


}
