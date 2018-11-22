package com.test.sbsagatest.saga;

import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * TODO: class comment
 */
public class Saga {

  private LinkedHashSet<ProcessStep> processSteps = new LinkedHashSet<>();
  private String processName;

  public Saga(final String processName) {
    this.processName = processName;
  }

  public Saga add(ProcessStep processStep) {
    processSteps.add(processStep);
    return this;
  }

  public LinkedHashSet<ProcessStep> getProcessSteps() {
    return this.processSteps;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "Saga{", "}")
        .add("processSteps=" + processSteps)
        .toString();
  }

  /**
   * Saga Executor
   */
  public static final class SagaExecutor {

    private Saga saga;
    private Deque<ProcessStep> sagaLog = new LinkedList<>();

    public SagaExecutor (final Saga saga) {
      this.saga = saga;
    }

    public SagaExecutor execute() {

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
//      sagaLog.descendingIterator().forEachRemaining(processStep -> processStep.getCompensatingAction().run());
      sagaLog.iterator().forEachRemaining(processStep -> processStep.getCompensatingAction().run());
    }

  }

}
