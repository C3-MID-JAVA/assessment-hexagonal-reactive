package ec.com.sofka.config;

import ec.com.sofka.gateway.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.math.BigDecimal;

@Configuration
@ComponentScan(basePackages = "ec.com.sofka",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$"),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TransactionRepository.class})
        })
public class UseCaseConfig {

    @Value("${app.costoDepositoCajero}")
    private BigDecimal costoDepositoCajero;

    @Value("${app.costoDepositoOtraCuenta}")
    private BigDecimal costoDepositoOtraCuenta;

    @Value("${app.costoDepositoSucursal}")
    private BigDecimal costoDepositoSucursal;

    @Value("${app.costoSeguroCompraWeb}")
    private BigDecimal costoSeguroCompraWeb;

    @Value("${app.costoCompraEstablecimiento}")
    private BigDecimal costoCompraEstablecimiento;

    @Value("${app.costoRetiro}")
    private BigDecimal costoRetiro;

    @Bean
    public BigDecimal costoDepositoCajero() {
        return costoDepositoCajero;
    }

    @Bean
    public BigDecimal costoDepositoOtraCuenta() {
        return costoDepositoOtraCuenta;
    }

    @Bean
    public BigDecimal costoDepositoSucursal() {
        return costoDepositoSucursal;
    }

    @Bean
    public BigDecimal costoSeguroCompraWeb() {
        return costoSeguroCompraWeb;
    }

    @Bean
    public BigDecimal costoCompraEstablecimiento() {
        return costoCompraEstablecimiento;
    }

    @Bean
    public BigDecimal costoRetiro() {
        return costoRetiro;
    }
}