package freezy.utils;

import de.taimos.totp.TOTP;
import freezy.entities.UserV1;
import freezy.repository.v1.UserRepositoryV1;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.security.SecureRandom;

@Service
public class FreazyOTPService {
    private final UserRepositoryV1 userRepository;

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Autowired
    public FreazyOTPService(UserRepositoryV1 userRepository) {
        this.userRepository = userRepository;
    }

    public void generateSecretKey(String phNo) {
        UserV1 user = userRepository.findByPhoneNumber(phNo);
        if (null != user) {
            String secretKey = generateSecret();
            user.setSecretKey(secretKey);
            userRepository.save(user);
            sendSecretKeySms(user.getPhoneNumber(), secretKey);
        }
    }

    public boolean verifyOtp(String phNo, String otp) {
        UserV1 user = userRepository.findByPhoneNumber(phNo);
        if (null != user) {
            String secretKey = user.getSecretKey();
            String generatedOtp = TOTP.getOTP(secretKey);
            if (generatedOtp.equals(otp)) {
//                user.setOtpVerified(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    private String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    private void sendSecretKeySms(String phoneNumber, String secretKey) {
        String messageBody = "Your OTP code is: " + TOTP.getOTP(secretKey);

        com.twilio.Twilio.init(twilioAccountSid, twilioAuthToken);
        Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(twilioPhoneNumber), messageBody).create();
    }
}

