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

  @Bean
  public RouteBuilder route() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("direct:start").to("log:end?level=INFO");
        from("timer://foo?period=1000").process(new Processor() {
          @Override
          public void process(Exchange exchange) throws Exception {
            System.out.println("some");
            exchange.setOut(new Message() {
              @Override
              public String getMessageId() {
                return null;
              }

              @Override
              public void setMessageId(String messageId) {

              }

              @Override
              public Exchange getExchange() {
                return null;
              }

              @Override
              public boolean isFault() {
                return false;
              }

              @Override
              public void setFault(boolean fault) {

              }

              @Override
              public Object getHeader(String name) {
                return null;
              }

              @Override
              public Object getHeader(String name, Object defaultValue) {
                return null;
              }

              @Override
              public <T> T getHeader(String name, Class<T> type) {
                return null;
              }

              @Override
              public <T> T getHeader(String name, Object defaultValue, Class<T> type) {
                return null;
              }

              @Override
              public void setHeader(String name, Object value) {

              }

              @Override
              public Object removeHeader(String name) {
                return null;
              }

              @Override
              public boolean removeHeaders(String pattern) {
                return false;
              }

              @Override
              public boolean removeHeaders(String pattern, String... excludePatterns) {
                return false;
              }

              @Override
              public Map<String, Object> getHeaders() {
                return null;
              }

              @Override
              public void setHeaders(Map<String, Object> headers) {

              }

              @Override
              public boolean hasHeaders() {
                return false;
              }

              @Override
              public Object getBody() {
                return "Some Body";
              }

              @Override
              public Object getMandatoryBody() throws InvalidPayloadException {
                return null;
              }

              @Override
              public <T> T getBody(Class<T> type) {
                return null;
              }

              @Override
              public <T> T getMandatoryBody(Class<T> type) throws InvalidPayloadException {
                return null;
              }

              @Override
              public void setBody(Object body) {

              }

              @Override
              public <T> void setBody(Object body, Class<T> type) {

              }

              @Override
              public Message copy() {
                return null;
              }

              @Override
              public void copyFrom(Message message) {

              }

              @Override
              public void copyAttachments(Message message) {

              }

              @Override
              public DataHandler getAttachment(String id) {
                return null;
              }

              @Override
              public Set<String> getAttachmentNames() {
                return null;
              }

              @Override
              public void removeAttachment(String id) {

              }

              @Override
              public void addAttachment(String id, DataHandler content) {

              }

              @Override
              public Map<String, DataHandler> getAttachments() {
                return null;
              }

              @Override
              public void setAttachments(Map<String, DataHandler> attachments) {

              }

              @Override
              public boolean hasAttachments() {
                return false;
              }

              @Override
              public String createExchangeId() {
                return null;
              }
            });
          }
        }).to("log:end?level=INFO");
      }
    };
  }

  @Bean
  public CommandLineRunner runner() {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        camelContext.createProducerTemplate().sendBody("direct:start", "Hello, world");
      }
    };
  }


  public static void main(String[] args) {
    final ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootCamelApplication.class, args);
    CamelSpringBootApplicationController applicationController =
        applicationContext.getBean(CamelSpringBootApplicationController.class);
    applicationController.blockMainThread();
  }
}
