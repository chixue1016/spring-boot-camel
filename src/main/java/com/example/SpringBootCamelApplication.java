package com.example;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RoutesDefinition;
import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class SpringBootCamelApplication {

  @Autowired
  CamelContext camelContext;

  @Autowired
  private ProducerTemplate producerTemplate;

  @Bean
  public Upper upper() {
    return new Upper();

  }

  @Bean
  public RouteBuilder route() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
//        from("direct:start").to("log:end?level=INFO");
        from("timer://foo?period=1000").process(exchange -> {
          exchange.getIn().setBody("Hello, world with Java!");
        }).bean(upper()).to("log:end?level=INFO");
      }
    };
  }

  public static void main(String[] args) {
    final ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootCamelApplication.class, args);
    CamelSpringBootApplicationController applicationController =
        applicationContext.getBean(CamelSpringBootApplicationController.class);
    applicationController.run();
  }

  public class Upper {
    // both methodes are OK

    //    public String convert(String s) {
    //      return s.toUpperCase();
    //    }


    public void process(Exchange ex) {
//      System.out.println(ex.getPattern());
      ex.getIn().setBody(ex.getIn().getBody().toString().toUpperCase());
    }
  }
}
