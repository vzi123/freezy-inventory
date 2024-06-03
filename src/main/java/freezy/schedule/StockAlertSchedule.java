package freezy.schedule;

import freezy.entities.Inventory;
import freezy.entities.StockAlerts;
import freezy.repository.InventoryRepository;
import freezy.repository.StockAlertsRepository;
import freezy.utils.Constants;
import freezy.utils.StockAlertEmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StockAlertSchedule {

    @Autowired
    StockAlertEmailService stockAlertEmailService;


    @Scheduled(cron = "0 */5 * ? * *")
    public void runEvey5Minutes() {
        System.out.println("Current time is :: " + LocalDate.now());
    }


}
