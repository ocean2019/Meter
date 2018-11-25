package com.example.configuer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitmqConfiguer implements RabbitListenerConfigurer {
	
	@Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

	
	
	
	@Bean(name="one")
    public Queue queue() {
         return new Queue("one",true);
    }
	
	@Bean(name="DExchange")
	public DirectExchange DExchange() {
		return new DirectExchange("DExchange",true,false);
	}
	
	@Bean
    Binding bindingExchangeMessage(@Qualifier("one") Queue queueMessage, @Qualifier("DExchange") DirectExchange exchange) {
		
        return BindingBuilder.bind(queueMessage).to(exchange).with("ww");
    }
	
	
//	 @Bean
//	    public MessageConverter jsonMessageConverter() {
//	        return new Jackson2JsonMessageConverter();
//	    }
//	@Bean(name="wis_collector")
//    public Queue queue() {
//         return new Queue("wis_collector",true);
//    }
//	
//	@Bean(name="wis_exchange")
//	public DirectExchange DExchange() {
//		return new DirectExchange("wis_exchange",true,false);
//		
//	}
//	
//	@Bean
//    Binding bindingExchangeMessage(@Qualifier("wis_collector") Queue queueMessage, @Qualifier("wis_exchange") DirectExchange exchange) {
//		
//        return BindingBuilder.bind(queueMessage).to(exchange).with("wis_collector");
//    }
//	

}
