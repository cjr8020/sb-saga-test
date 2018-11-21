package com.test.sbsagatest.saga;

import java.util.Deque;

/**
 * TODO: class comment
 */
public class SagaRepository {

  private Deque<SagaAction> actionStack;

  public Deque<SagaAction> getActionStack() {
    return actionStack;
  }

  public void setActionStack(Deque<SagaAction> actionStack) {
    this.actionStack = actionStack;
  }

  public void clear() {
    this.actionStack = null;
  }
}
