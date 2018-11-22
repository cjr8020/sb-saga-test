package com.test.sbsagatest.saga;

import java.util.StringJoiner;

/**
 * Represents a processing step with a defined compensating action.
 */
public class ProcessStep {

  private Runnable action;
  private Runnable compensatingAction;

  public ProcessStep(Runnable action, Runnable compensatingAction) {
    this.action = action;
    this.compensatingAction = compensatingAction;
  }

  public Runnable getAction() {
    return action;
  }

  public void setAction(Runnable action) {
    this.action = action;
  }

  public Runnable getCompensatingAction() {
    return compensatingAction;
  }

  public void setCompensatingAction(Runnable compensatingAction) {
    this.compensatingAction = compensatingAction;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "ProcessStep{", "}")
        .add("action=" + action)
        .add("compensatingAction=" + compensatingAction)
        .toString();
  }
}
