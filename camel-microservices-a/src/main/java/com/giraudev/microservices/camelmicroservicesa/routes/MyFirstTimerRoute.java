package com.giraudev.microservices.camelmicroservicesa.routes;

import java.time.LocalDateTime;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//desabilitando o routes
//@Component
public class MyFirstTimerRoute extends RouteBuilder {

  @Autowired
  private GetCurrentTimeBean getCurrentTimeBean;

  @Autowired
  private SimpleLoggingProcessingComponent loggingComponent;

  @Override
  public void configure() throws Exception {
    // queue --> timer
    // transformation
    // databse --> log
    //.transform().constant("Time now is" + LocalDateTime.now())
    //melhor espeficar nome do bean
    //  quando tem apenas 1 Bean:      .bean(getCurrentTimeBean)
    from("timer:first-timer")
        .log("${body}")
        .transform().constant("My Constant Message")
        .log("${body}")
        .bean(getCurrentTimeBean)
        .log("${body}")
        .process(new SimpleLoggingProcessor())
        //.bean(loggingComponent) ou usamos a classe SingleLoggingProcessor
        .to("log:first-timer");

  }
}

@Component
class GetCurrentTimeBean {
  public String getCurrentTime() {
    return "Time now is " + LocalDateTime.now();
  }
}

@Component
class SimpleLoggingProcessingComponent {
  private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

  public void process(String message) {
    logger.info("SmpleLoggingProcessingComponent {}", message);
  }
}

class SimpleLoggingProcessor implements Processor {
  private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

  @Override
  public void process(Exchange exchange) throws Exception {
    logger.info("SmpleLoggingProcessor {}", exchange.getMessage().getBody());

  }
}