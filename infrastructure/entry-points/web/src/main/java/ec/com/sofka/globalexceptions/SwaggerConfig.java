package ec.com.sofka.globalexceptions;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springdoc.webflux.api.OpenApiWebfluxResource;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.web.reactive.function.server.RouterFunctions;

import java.net.URI;
/*
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi
                .builder()
                .group("public")
                .packagesToScan("ec.com.sofka.router") // Escanea el paquete donde están tus routers
                .build();
    }
}
*/