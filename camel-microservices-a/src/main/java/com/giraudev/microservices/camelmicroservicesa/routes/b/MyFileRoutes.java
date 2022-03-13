package com.giraudev.microservices.camelmicroservicesa.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFileRoutes extends RouteBuilder {

  @Override
  public void configure() throws Exception {
      from("file:files/input")
          .log("${body}")
          .to("file:files/output");

  }
}
