package com.scm.wms.warehouse.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.Version;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Warehouse Management System",
                version = "1.0",
                description = "Rest APIs for warehouse and product module"

        )
)
public class OpenApiConfig {
}
