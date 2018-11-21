package com.test.sbsagatest.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/say")
public class TestResource {

  private static final Logger logger = LoggerFactory.getLogger(TestResource.class);


  @RequestMapping(
      value = "/hello",
      produces = "application/json; charset=UTF-8",
      method = RequestMethod.GET
  )
  public String sayHello() {
    logger.info(" --------------- sayHello ------------------");

    return "Hello";
  }

}
