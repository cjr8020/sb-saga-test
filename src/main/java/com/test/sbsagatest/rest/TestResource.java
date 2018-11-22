package com.test.sbsagatest.rest;

import com.test.sbsagatest.saga.ProcessStep;
import com.test.sbsagatest.saga.Saga;
import com.test.sbsagatest.saga.Saga.SagaExecutor;
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


    ProcessStep processStepOne = new ProcessStep(
        () -> System.out.println("Action 1"),
        () -> System.out.println("Undo Action 1")
    );

    ProcessStep processStepTwo = new ProcessStep(
        () -> System.out.println("Action 2"),
        () -> System.out.println("Undo Action 2")
    );

    ProcessStep processStepThree = new ProcessStep(
        () -> {
          System.out.println("Action 3");
          throw new RuntimeException("Action 3 Error");
        },
        () -> System.out.println("Undo Action 3")
    );

    ProcessStep processStepFour = new ProcessStep(
        () -> System.out.println("Action 4"),
        () -> System.out.println("Undo Action 4")
    );

    new Saga("test-process")
        .add(processStepOne)
        .add(processStepTwo)
        .add(processStepThree)
        .add(processStepFour)
        .execute();


    return "Hello";
  }

}
