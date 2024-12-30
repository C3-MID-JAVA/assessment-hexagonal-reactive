package ec.com.sofka.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${amqp.exchange.default}")
    public String EXCHANGE_NAME;
    @Value("${amqp.queue.default}")
    public  String QUEUE_NAME ;
    @Value("${amqp.routing.key.default}")
    public  String ROUTING_KEY;

    @Value("${amqp.exchange.account}")
    private String EXCHANGE;
    @Value("${amqp.queue.account}")
    private String QUEUE;
    @Value("${amqp.routing.key.account}")
    private String ROUTINGKEY;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue accountQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public DirectExchange accountExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding accountBinding(DirectExchange accountExchange, Queue accountQueue) {
        return BindingBuilder.bind(accountQueue).to(accountExchange).with(ROUTINGKEY);
    }
}
