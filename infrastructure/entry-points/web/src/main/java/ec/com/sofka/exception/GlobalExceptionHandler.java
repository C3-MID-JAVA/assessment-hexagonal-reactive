package ec.com.sofka.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.sofka.exception.model.ErrorResponse;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        HttpStatus status = resolveHttpStatus(ex);
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());

        return response.writeWith(Mono.fromSupplier(() -> createBuffer(response, errorResponse)));
    }

    private HttpStatus resolveHttpStatus(Throwable ex) {
        if (ex instanceof NotFoundException) return HttpStatus.NOT_FOUND;
        if (ex instanceof ValidationException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof InternalServerException) return HttpStatus.INTERNAL_SERVER_ERROR;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private DataBuffer createBuffer(ServerHttpResponse response, ErrorResponse errorResponse) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            return response.bufferFactory().wrap(bytes);
        } catch (Exception e) {
            return response.bufferFactory().wrap(new byte[0]);
        }
    }

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
