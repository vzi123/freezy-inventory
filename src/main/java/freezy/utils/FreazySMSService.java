package freezy.utils;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import org.springframework.stereotype.Service;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FreazySMSService {


    public Object sendSms(String phoneNumber, String message) {
        try {
            if (isPhoneNumberValid(phoneNumber)) {
                Twilio.init("AC8903c55131234b42768cc0f4c60360b2", "f09413ea3aaf16b20c40b7202490dd06");
                PhoneNumber to = new PhoneNumber(phoneNumber);
                PhoneNumber from = new PhoneNumber("+14052679902");
                return Message.creator(to,from,message).create();
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
