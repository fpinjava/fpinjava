package com.fpinjava.state.exercise12_09;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class StateTest {

  @Test
  public void testMap() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Boolean, RNG> result1 = Random.booleanRnd.run.apply(rng);
    assertTrue(result1._1);
    Tuple<Boolean, RNG> result2 = Random.booleanRnd.run.apply(result1._2);
    assertTrue(result2._1);
    Tuple<Boolean, RNG> result3 = Random.booleanRnd.run.apply(result2._2);
    assertTrue(result3._1);
  }

  @Test
  public void testMap2() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Tuple<Integer, Integer>, RNG> result = Random.intPairRnd.run.apply(rng);
    assertEquals(Integer.valueOf(-1155484576), result._1._1);
    assertEquals(Integer.valueOf(-723955400), result._1._2);
    Tuple<Integer, RNG> t = Random.intRnd.run.apply(result._2);
    assertEquals(Integer.valueOf(1033096058), t._1);
  }

  public static State<RNG, List<Integer>> makeListOfNonMultipleOfFive(int length) {
    return Random.sequence(List.fill(length, StateTest::supplyRandomNotMultipleOfFiveInteger));
  }

  public static State<RNG, Integer> supplyRandomNotMultipleOfFiveInteger() {
    return Random.notMultipleOfFiveRnd;
  }

  public static Boolean notMultipleOfFive(int i) {
    return i % 5 != 0;
  }

  @Test
  public void testFlatMap() {
    RNG rng = JavaRNG.rng(0);
    Tuple<List<Integer>, RNG> result = makeListOfNonMultipleOfFive(300).run.apply(rng);
    assertTrue(result._1.forAll(StateTest::notMultipleOfFive));
  }

  @Test
  public void testSequence() {

  }
}
