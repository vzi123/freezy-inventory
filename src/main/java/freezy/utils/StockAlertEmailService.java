package freezy.utils;


import freezy.entities.Inventory;
import freezy.entities.StockAlerts;
import freezy.repository.InventoryRepository;
import freezy.repository.StockAlertsRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockAlertEmailService {

    @Autowired
    StockAlertsRepository stockAlertsRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public void checkAndSendEmail() throws Exception{
        Map<String, Inventory> alertInventory = new HashMap<>();
        List<StockAlerts> stockAlerts = stockAlertsRepository.findAll();
        List<Inventory> inventories = inventoryRepository.findAll();
        Map<String, Integer> alertsMap = new HashMap<>();
        stockAlerts.forEach(e-> alertsMap.put(e.getProduct().getId(),e.getAlertQuantity() ));

        for (Inventory inventory: inventories) {
            if(null != alertsMap.get(inventory.getProduct().getId()) && inventory.getInventory() < alertsMap.get(inventory.getProduct().getId())){
                alertInventory.put(inventory.getProduct().getId(), inventory);
            }
        }

        if(alertInventory.size() > 0){
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");
            helper.setTo("no-reply@tripor.ai");
            helper.setTo(new InternetAddress("vedaprakash.n@gmail.com"));
            helper.setSubject("Stock Alert !");
            mailMessage.setContent(generateReportMessage(alertInventory), "text/html");
            javaMailSender.send(mailMessage);

        }
    }

    public String generateReportMessage(Map<String,Inventory> alertInventory) {
        StringBuilder stringBuilder = generateCommonHtmlHead();

        if(alertInventory.size() > 0){
            for (String key:
                    alertInventory.keySet()) {
                Inventory inventory = alertInventory.get(key);
                stringBuilder.append("<tr>");
                stringBuilder.append("<td>").append(inventory.getProduct().getId()).append("</td>");
                stringBuilder.append("<td>").append(inventory.getProduct().getName()).append("</td>");
                stringBuilder.append("<td>").append(inventory.getInventory()).append("</td>");
                stringBuilder.append("</tr>");
            }
            generateCommonFooter(stringBuilder);
        }
        return stringBuilder.toString();
    }

    private StringBuilder generateCommonHtmlHead() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("<head>Hi Admin</head>")
                .append("<p>The following stock is below the configured limit. Please procure the following products.</p>")
                .append("</head>")
                .append("<body>")
                .append("<table border=2 cellpadding=\"10\">")
                .append("<tr>")
                .append("<th>Product Id</th><th>Product Name</th><th>Stock Left</th>")
                .append("</tr>");
    }

    private void generateCommonFooter(StringBuilder stringBuilder) {
        stringBuilder.append("</table><p>Thanks</p>\n" +
                "<p>Freazy Admin</p></body>");
    }
}
