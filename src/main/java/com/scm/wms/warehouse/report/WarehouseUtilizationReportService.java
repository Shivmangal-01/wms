package com.scm.wms.warehouse.report;

import com.scm.wms.warehouse.entities.Warehouse;
import com.scm.wms.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseUtilizationReportService {

    private final WarehouseRepository warehouseRepository;

    public void generateDailyWarehouseReport() {

        List<Warehouse> warehouses = warehouseRepository.findByIsDeletedFalse();

        String fileName =
                "warehouse-report-" +
                        LocalDate.now() + ".xlsx";

        try (XSSFWorkbook workbook =
                     new XSSFWorkbook()) {

            Sheet sheet =
                    workbook.createSheet(
                            "Warehouse Utilization"
                    );

            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0)
                    .setCellValue("Warehouse Code");

            headerRow.createCell(1)
                    .setCellValue("Warehouse Name");

            headerRow.createCell(2)
                    .setCellValue("Location");

            headerRow.createCell(3)
                    .setCellValue("Total Capacity");

            headerRow.createCell(4)
                    .setCellValue("Used Capacity");

            headerRow.createCell(5)
                    .setCellValue("Free Capacity");

            headerRow.createCell(6)
                    .setCellValue("Status");

            int rowNum = 1;

            for (Warehouse warehouse : warehouses) {

                Row row = sheet.createRow(rowNum++);


                int used = warehouse.getUsedCapacity() != null ? warehouse.getUsedCapacity() : 0;
                int freeCapacity = warehouse.getCapacity() - used;

                row.createCell(0)
                        .setCellValue(
                                warehouse.getWarehouseCode()
                        );

                row.createCell(1)
                        .setCellValue(
                                warehouse.getName()
                        );

                row.createCell(2)
                        .setCellValue(
                                warehouse.getLocation()
                        );

                row.createCell(3)
                        .setCellValue(
                                warehouse.getCapacity()
                        );

                row.createCell(4).setCellValue(used);

                row.createCell(5)
                        .setCellValue(
                                freeCapacity
                        );

                row.createCell(6)
                        .setCellValue(
                                warehouse.getStatus().name()
                        );
            }

            FileOutputStream out =
                    new FileOutputStream(fileName);

            workbook.write(out);

            out.close();

            log.info(
                    "Daily warehouse report generated successfully: {}",
                    fileName
            );

        } catch (Exception e) {

            log.error(
                    "Error while generating warehouse report",
                    e
            );

            throw new RuntimeException(
                    "Failed to generate warehouse report"
            );
        }
    }
}
