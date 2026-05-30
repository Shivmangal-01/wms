package com.scm.wms.product.scheduler;


import com.scm.wms.product.report.ProductPdfReportService;
import com.scm.wms.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductStockScheduler {
    private final ProductRepository productRepository;
    private final ProductPdfReportService productPdfReportService;

    @Scheduled(cron = "0 0 10 * * *")
    public void generateDailyInventoryReport() {

        log.info("Daily inventory report generation started");

        productPdfReportService.generateDailyInventoryPdfReport();

        log.info("Daily inventory report generated successfully");
    }


}
