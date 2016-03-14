package com.fpinjava.state.exercise12_11;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class AtmTest {

  @Test
  public void testSimulateMachine1() {
    assertEquals("(0,[NIL])", test(List.list()).toString());
  }

  @Test
  public void testSimulateMachine2() {
    assertEquals("(100,[Success(100), NIL])", test(List.list(new Deposit(100))).toString());
  }

  @Test
  public void testSimulateMachine3() {
    assertEquals("(50,[Success(-50), Failure(java.lang.IllegalStateException: Insufficient balance), Success(100), NIL])", test(List.list(new Deposit(100), new Withdraw(150), new Withdraw(50))).toString());
  }

  @Test
  public void testSimulateMachine4() {
    assertEquals("(50,[Failure(java.lang.IllegalStateException: Insufficient balance), Success(-50), Success(100), NIL])", test(List.list(new Deposit(100), new Withdraw(50), new Withdraw(150))).toString());
  }

  @Test
  public void testSimulateMachine5() {
    assertEquals("(100,[Success(-150), Success(200), Failure(java.lang.IllegalStateException: Insufficient balance), Success(-50), Success(100), NIL])", test(List.list(new Deposit(100), new Withdraw(50), new Withdraw(150), new Deposit(200), new Withdraw(150))).toString());
  }

  private Outcome test(List<Input> inputs) {
    return Atm.createMachine().process(inputs).eval(new Outcome(0, List.list()));
  }
}
