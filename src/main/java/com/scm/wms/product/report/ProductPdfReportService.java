package com.scm.wms.product.report;

import com.scm.wms.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.scm.wms.product.entities.Product;
import com.scm.wms.product.enums.ProductStatus;


import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPdfReportService {
    private final ProductRepository productRepository;

    public void generateDailyInventoryPdfReport() {

        try {
            File directory = new File("reports");

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "reports/inventory-report-" + LocalDate.now() + ".pdf";

            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            document.add(new Paragraph("DAILY INVENTORY REPORT"));
            document.add(new Paragraph("Date: " + LocalDate.now()));
            document.add(new Paragraph(" "));

            long totalProducts = productRepository.countByIsDeletedFalse();

            long activeProducts = productRepository.countByStatusAndIsDeletedFalse(ProductStatus.ACTIVE);

            long inactiveProducts = productRepository.countByStatusAndIsDeletedFalse(ProductStatus.INACTIVE);

            List<Product> lowStockProducts = productRepository.findLowStockProducts();

            Long totalInventoryQuantity = productRepository.getTotalInventoryQuantity();

            document.add(new Paragraph("Total Products: " + totalProducts));
            document.add(new Paragraph("Active Products: " + activeProducts));
            document.add(new Paragraph("Inactive Products: " + inactiveProducts));
            document.add(new Paragraph("Low Stock Products: " + lowStockProducts.size()));
            document.add(new Paragraph("Total Inventory Quantity: " + totalInventoryQuantity));
            document.close();
            log.info("PDF report generated successfully: {}", fileName);

        } catch (Exception e) {
            log.error("Error generating PDF report", e);
        }
    }
}
