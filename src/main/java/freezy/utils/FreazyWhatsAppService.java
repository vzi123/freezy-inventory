package freezy.utils;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class FreazyWhatsAppService {


    public Object sendMessage(String phoneNumber, String message) {
        try {
            if (isPhoneNumberValid(phoneNumber)) {
                Twilio.init("AC8903c55131234b42768cc0f4c60360b2", "81d1c5678e917439be647ef6e2ef1313");
                PhoneNumber to = new PhoneNumber("whatsapp:"+phoneNumber);
                PhoneNumber from = new PhoneNumber("whatsapp:+19705000909");
                return Message.creator(to,from,"Welcome to Freazy. Your one-stop solution for all your cooling solutions.").create();
            } else {
                throw new IllegalArgumentException(
                        "Phone number [" + phoneNumber + "] is not a valid number"
                );
            }
        } catch (ApiException exception) {

        }
        return null;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }
}
