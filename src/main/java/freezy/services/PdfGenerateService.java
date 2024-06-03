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


    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws Exception{
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            File yourFile = new File(Constants.FILE_LOCATION + pdfFileName);
            yourFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(Constants.FILE_LOCATION + pdfFileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
    }


    public void generateQuotation(Quotation quotation) throws Exception{
        User customer = quotation.getUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setName(customer.getFirst_name() + " " + customer.getLast_name());
        userDTO.setEmail(customer.getEmail());
        userDTO.setPhoneNumber(customer.getPhone_number());
        userDTO.setAddress(customer.getAddress());

        Map<String, Object> data = new HashMap<String, Object>();
        List<QuotationMailDTO> quotations = new ArrayList<QuotationMailDTO>();
        List<QuotationItems> quotationItems = quotation.getQuotationItems();
        Integer quotationTotal = 0;
        for (QuotationItems item: quotationItems
             ) {
            QuotationMailDTO quotationMailDTO = new QuotationMailDTO();
            quotationMailDTO.setPrice(100);
            quotationMailDTO.setProductName(item.getProduct().getName());
            quotationMailDTO.setQuantity(item.getQuantity());
            quotationMailDTO.setSubTotal(quotationMailDTO.getPrice() * quotationMailDTO.getQuantity());
            quotationTotal = quotationTotal + quotationMailDTO.getSubTotal();
            quotations.add(quotationMailDTO);
        }
        data.put("quotation", quotations);
        data.put("customer",userDTO);
        data.put("total", quotationTotal);
        generatePdfFile("quotation", data,quotation.getId() + " - " + "quotation.pdf");
    }
}
