package co.com.sofkau.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.sofkau",
includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
})
public class UseCaseConfig {
}
