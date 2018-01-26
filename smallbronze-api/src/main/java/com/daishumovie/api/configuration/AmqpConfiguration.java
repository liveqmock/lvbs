package com.daishumovie.api.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.daishumovie.base.Constants;

/**
 * @author zhuruisong on 2017/9/18
 * @since 1.0
 */
//@Configuration
public class AmqpConfiguration {

    @Autowired
    public AmqpConfiguration(AmqpAdmin amqpAdmin) {
        Queue queue = QueueBuilder.durable(Constants.QUEUE_RECOMMEND).build();
        Exchange exchange = ExchangeBuilder.directExchange(Constants.EXCHANGE_RECOMMEND).build();
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(Constants.ROUTING_KEY_RECOMMEND).noargs();
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareBinding(binding);

    }
}
