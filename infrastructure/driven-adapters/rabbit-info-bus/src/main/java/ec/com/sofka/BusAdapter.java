package ec.com.sofka;

import ec.com.sofka.gateway.BusMessageGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BusAdapter implements BusMessageGateway {

    @Value("${amqp.exchange.default}")
    private String exchange;

    @Value("${amqp.routing.key.default}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public BusAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsg(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
