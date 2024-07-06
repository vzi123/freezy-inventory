package freezy.services;

import freezy.dto.QuotationMailDTO;
import freezy.dto.UserDTO;
import freezy.dto.v1.DCDTOV1;
import freezy.entities.*;
import freezy.entities.v1.*;
import freezy.repository.v1.InventoryLogRepositoryV1;
import freezy.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateService.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    InventoryLogRepositoryV1 inventoryLogRepositoryV1;


    public File generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws Exception{
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            Resource resource = new ClassPathResource("/pdfs/");

            String resourcePath = resource.getFile().getAbsolutePath();
            File directory = new File(resourcePath);

            // Ensure the directory exists
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File yourFile = new File(directory, pdfFileName);
            yourFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream((yourFile));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();

            return yourFile;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public File generateQuotation(Quotation quotation) throws Exception{
        User customer = quotation.getUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setName(customer.getFirst_name() + " " + customer.getLast_name());
        userDTO.setEmail(customer.getEmail());
        userDTO.setPhoneNumber(customer.getPhone_number());
        userDTO.setAddress(customer.getAddress());
        String projectName = quotation.getProject().getName();

        Map<String, Object> data = new HashMap<String, Object>();
        List<QuotationMailDTO> quotations = new ArrayList<QuotationMailDTO>();
        List<QuotationItems> quotationItems = quotation.getQuotationItems();
        double grandTotal = 0;
        double subTotal = 0;
        double discount = quotation.getDiscount();
        for (QuotationItems item: quotationItems
             ) {
            QuotationMailDTO quotationMailDTO = new QuotationMailDTO();
            quotationMailDTO.setId(item.getProduct().getId());
            quotationMailDTO.setDescription(item.getProduct().getName());
            quotationMailDTO.setQuantity(item.getQuantity());
            quotationMailDTO.setPrice(item.getProduct().getCost());
            quotationMailDTO.setDiscountPrice((int)(item.getProduct().getCost() * discount / 100));
            quotations.add(quotationMailDTO);
            subTotal = subTotal + (item.getQuantity() * item.getProduct().getCost());
        }
        double discountAmount = subTotal * discount / 100;
        grandTotal = subTotal - discountAmount;
        data.put("quotation", quotations);
        data.put("customer",userDTO);
        data.put("grandTotal", grandTotal);
        data.put("subTotal", subTotal);
        data.put("discount", discountAmount);
        data.put("discountPercentage", discount);
        data.put("projectName", projectName);
        return generatePdfFile("newQuotation", data,quotation.getId() + "-" + "quotation.pdf");
    }

    public File generateDeliveryChallan(ConsignmentV1 consignmentV1) throws Exception{
        UserV1 customer = consignmentV1.getCreatedFor();
        UserDTO userDTO = new UserDTO();
        userDTO.setName(customer.getFirst_name());
        userDTO.setEmail(customer.getEmail());
        userDTO.setPhoneNumber(customer.getPhone_number());
        userDTO.setAddress(customer.getAddress());

        Map<String, Object> data = new HashMap<String, Object>();
        List<DCDTOV1> products = new ArrayList<DCDTOV1>();
        List<DCDTOV1> accessories = new ArrayList<DCDTOV1>();
        List<InventoryLogV1> inventoryLogV1s = inventoryLogRepositoryV1.findAllByConsignment(consignmentV1);
        for (InventoryLogV1 log: inventoryLogV1s) {
            if(null != log.getType() && log.getType().equals(InventoryTypeV1.PRODUCT)){
                DCDTOV1 dcdto = new DCDTOV1();
                ProductV1 productV1 = log.getInventory().getProduct();
                dcdto.setId(productV1.getId());
                dcdto.setDescription(productV1.getName());
                dcdto.setHsnNo(productV1.getHsnNo());
                products.add(dcdto);
            }
            if(null != log.getType() && log.getType().equals(InventoryTypeV1.ACCESSORY)){
                DCDTOV1 dcdto = new DCDTOV1();
                AccessoryV1 accessory = log.getInventory().getAccessory();
                dcdto.setId(accessory.getId());
                dcdto.setDescription(accessory.getName());
                dcdto.setQuantity(log.getQuantity().toString());
                accessories.add(dcdto);
            }

        }
        data.put("products", products);
        data.put("accessories", accessories);
        data.put("customer",userDTO);
        data.put("dcId", consignmentV1.getId());
        return generatePdfFile("deliveryChallan", data,consignmentV1.getId() + "-" + "dc.pdf");
    }
}
