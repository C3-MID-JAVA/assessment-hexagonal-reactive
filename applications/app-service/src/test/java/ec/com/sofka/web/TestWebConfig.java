package ec.com.sofka.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.sofka.exception.GlobalExceptionHandler;
import ec.com.sofka.handler.UserHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestWebConfig {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
        return new GlobalExceptionHandler(objectMapper);
    }
}
