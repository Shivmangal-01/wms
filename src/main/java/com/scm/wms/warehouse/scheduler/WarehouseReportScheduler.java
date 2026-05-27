package com.scm.wms.warehouse.scheduler;


import com.scm.wms.warehouse.report.WarehouseUtilizationReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarehouseReportScheduler {

    private final WarehouseUtilizationReportService
            reportService;

    @Scheduled(cron = "0 0 1 * * *")
    public void generateWarehouseReport() {

        log.info(
                "Starting daily warehouse report generation..."
        );

        reportService.generateDailyWarehouseReport();

        log.info(
                "Daily warehouse report generation completed."
        );
    }
}
