package com.fpinjava.state.exercise12_07;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class RandomTest {

  public static Random<List<Integer>> makeListOfNonMultipleOfFive(int length) {
    return Random.sequence(List.fill(length, RandomTest::supplyRandomNotMultipleOfFiveInteger));
  }

  public static Random<Integer> supplyRandomNotMultipleOfFiveInteger() {
    return Random.notMultipleOfFiveRnd;
  }

  public static Boolean notMultipleOfFive(int i) {
    return i % 5 != 0;
  }

  @Test
  public void testFlatMap() {
    RNG rng = JavaRNG.rng(0);
    Tuple<List<Integer>, RNG> result = makeListOfNonMultipleOfFive(300).apply(rng);
    assertTrue(result._1.forAll(RandomTest::notMultipleOfFive));
  }


}
