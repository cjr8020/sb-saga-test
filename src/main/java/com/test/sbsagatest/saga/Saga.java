package com.test.sbsagatest.saga;

import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Saga.
 */
public class Saga {

  private static final Logger logger = LoggerFactory.getLogger(Saga.class);

  private String sagaName;
  private LinkedHashSet<ProcessStep> processSteps = new LinkedHashSet<>();

  public Saga(final String sagaName) {
    this.sagaName = sagaName;
  }

  public Saga add(ProcessStep processStep) {
    processSteps.add(processStep);
    return this;
  }

  public LinkedHashSet<ProcessStep> getProcessSteps() {
    return this.processSteps;
  }

  public void execute() {
    new SagaExecutor(this).execute();
  }


  @Override
  public String toString() {
    return new StringJoiner(", ", "Saga{" + this.sagaName, "}")
        .add("processSteps=" + processSteps)
        .toString();
  }



  /**
   * Saga Executor
   */
  public static final class SagaExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SagaExecutor.class);

    private Saga saga;
    private Deque<ProcessStep> sagaLog = new LinkedList<>();

    private SagaExecutor (final Saga saga) {
      this.saga = saga;
    }

    private SagaExecutor execute() {
      logger.info("executing saga={}", this.saga.sagaName);

      try {

        for (ProcessStep processStep : this.saga.getProcessSteps()) {
          Runnable action = processStep.getAction();
          action.run();
          // only after the processing step is complete, push to the sagaLog
          this.sagaLog.push(processStep);
        }

      } catch (RuntimeException exception) {
        System.err.println("ERROR: " + exception.getMessage());
        rollback();
      }

      return this;
    }

    private void rollback() {
      sagaLog.iterator().forEachRemaining(processStep -> processStep.getCompensatingAction().run());
    }

  }

}
