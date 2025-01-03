package ec.com.sofka.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class HealthCheckRouter {

    @Bean
    public RouterFunction<ServerResponse> healthRoute() {
        return RouterFunctions.route(GET("/health"), request -> ServerResponse.ok().bodyValue("OK"));
    }
}
