package freezy.utils;

import freezy.dto.QuotationMailDTO;
import freezy.dto.UserDTO;
import freezy.dto.v1.DCDTOV1;
import freezy.entities.*;
import freezy.events.DCCreatedPublisher;
import freezy.services.v1.ConsignmentDetailServiceV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
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
    DCCreatedPublisher dcCreatedPublisher;

    @Autowired
    UtilsService utilsService;

    @Autowired
    FreazyS3Service freazyS3Service;

    @Autowired
    ConsignmentDetailServiceV1 consignmentDetailServiceV1;


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



    public byte[] generatePdfFileContents(String templateName, Map<String, Object> data, String pdfFileName) throws Exception{
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, false);
            renderer.finishPDF();

            return byteArrayOutputStream.toByteArray();
        }  catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public File generatePDFFile(String templateName, Map<String, Object> data, String pdfFileName) throws Exception{
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, false);
            renderer.finishPDF();
            File file = new File(pdfFileName);
            OutputStream os = new FileOutputStream(file);
            byteArrayOutputStream.writeTo(os);
            os.close();
            return file;
        }  catch (FileNotFoundException e) {
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

    public byte[] generateDeliveryChallan(Consignment consignment) throws Exception{
        UserV1 customer = consignment.getCreatedFor();
        UserDTO userDTO = new UserDTO();
        userDTO.setName(customer.getFirstName());
        userDTO.setEmail(customer.getEmail());
        userDTO.setPhoneNumber(customer.getPhoneNumber());
        userDTO.setAddress(customer.getAddress());

        Map<String, Object> data = new HashMap<String, Object>();
        List<DCDTOV1> products = new ArrayList<DCDTOV1>();
        List<DCDTOV1> accessories = new ArrayList<DCDTOV1>();
        List<ConsignmentDetails> details = consignmentDetailServiceV1.getDetailsByConsignment(consignment);
        for (ConsignmentDetails detail: details) {
            if(detail.getType().name().equalsIgnoreCase(InventoryType.PRODUCT.name())){
                DCDTOV1 dcdto = new DCDTOV1();
                Product product = detail.getProduct();
                dcdto.setId(product.getId());
                dcdto.setDescription(product.getName());
                dcdto.setHsnNo(product.getHsnNo());
                products.add(dcdto);
            }
            if(detail.getType().name().equalsIgnoreCase(InventoryType.ACCESSORY.name())){
                DCDTOV1 dcdto = new DCDTOV1();
                Accessory accessory = detail.getAccessory();
                dcdto.setId(accessory.getId());
                dcdto.setDescription(accessory.getName());
                dcdto.setQuantity(detail.getCount().toString());
                accessories.add(dcdto);
            }

        }
        data.put("products", products);
        data.put("accessories", accessories);
        data.put("customer",userDTO);
        data.put("dcId", consignment.getId());
        byte[] dcFile = generatePdfFileContents("deliveryChallan", data, consignment.getId() + "-" + "dc.pdf");
        FreazyMultipartFile file = new FreazyMultipartFile(dcFile, consignment.getId());
        String s3Link = freazyS3Service.uploadFile(file);
        System.out.println("S3 Link : " + s3Link);
        dcCreatedPublisher.publishEvent(utilsService.getSuperUser().getId(),s3Link,userDTO.getName() );
        return generatePdfFileContents("deliveryChallan", data, consignment.getId() + "-" + "dc.pdf");
    }
}
