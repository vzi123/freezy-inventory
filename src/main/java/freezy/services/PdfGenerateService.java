package freezy.services;

import freezy.dto.QuotationMailDTO;
import freezy.dto.UserDTO;
import freezy.entities.*;
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


    public File generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws Exception{
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            Resource resource = new ClassPathResource("/pdfs/");
            resource.getURL().getPath();
            File yourFile = new File((resource.getURL().getPath() + pdfFileName));
            yourFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream((resource.getURL().getPath() + pdfFileName));
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
}
