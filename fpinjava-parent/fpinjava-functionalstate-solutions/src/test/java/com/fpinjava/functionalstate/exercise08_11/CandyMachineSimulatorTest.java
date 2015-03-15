package com.fpinjava.functionalstate.exercise08_11;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class CandyMachineSimulatorTest {

  private static Input COIN = new Coin();
  private static Input TURN = new Turn();

  @Test
  public void testSimulateMachine1() {
    assertEquals("(true,10,0)", test(List.list(), 10));
  }

  @Test
  public void testSimulateMachine2() {
    assertEquals("(true,10,0)", test(List.list(TURN), 10));
  }

  @Test
  public void testSimulateMachine3() {
    assertEquals("(false,10,1)", test(List.list(COIN), 10));
  }

  @Test
  public void testSimulateMachine4() {
    assertEquals("(true,0,0)", test(List.list(COIN), 0));
  }

  @Test
  public void testSimulateMachine5() {
    assertEquals("(true,9,1)", test(List.list(COIN, TURN), 10));
  }

  @Test
  public void testSimulateMachine6() {
    assertEquals("(true,8,2)", test(List.list(COIN, TURN, COIN, TURN), 10));
  }

  @Test
  public void testSimulateMachine7() {
    assertEquals("(true,2,1)", test(List.list(COIN, COIN, COIN, TURN), 3));
  }

  @Test
  public void testSimulateMachine8() {
    assertEquals("(true,4,6)", test(List.list(COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN), 10));
  }

  @Test
  public void testSimulateMachine9() {
    assertEquals("(true,0,10)", test(List.list(COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN, COIN, TURN), 10));
  }

  private String test(List<Input> inputs, int coins) {
    return CandyMachineSimulator.simulateMachine(inputs).eval(new Machine(true, coins, 0)).toString();
  }

}
