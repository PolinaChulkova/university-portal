package ru.university.portal.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin ampqAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue studentQueue() {
        return new Queue("studentQueue");
    }

    @Bean
    public Queue teacherQueue() {
        return new Queue("teacherQueue");
    }

   @Bean
   public DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
   }

    @Bean
    public Binding studentBinding() {
        return BindingBuilder.bind(studentQueue()).to(directExchange()).with("studentQueue");
    }

    @Bean
    public Binding teacherBinding() {
        return BindingBuilder.bind(teacherQueue()).to(directExchange()).with("teacherQueue");
    }
}