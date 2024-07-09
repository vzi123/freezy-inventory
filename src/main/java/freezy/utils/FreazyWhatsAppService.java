package freezy.utils;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FreazyWhatsAppService {

    Logger logger;

    public Object sendMessage(String phoneNumber, String message) {
        try {
            if (isPhoneNumberValid(phoneNumber)) {
                Twilio.init("AC8903c55131234b42768cc0f4c60360b2", "c15ffda4a11cf6bce82abb32e79e324a");
                PhoneNumber to = new PhoneNumber("whatsapp:"+phoneNumber);
                PhoneNumber from = new PhoneNumber("whatsapp:+14052679902");
                return Message.creator(to,from,message).create();
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
}
