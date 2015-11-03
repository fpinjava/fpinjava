package com.fpinjava.state.exercise12_01;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratorTest {

  @Test
  public void testInteger() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Integer, RNG> t1 = Generator.integer(rng);
    assertEquals(Integer.valueOf(-1155484576), t1._1);
    Tuple<Integer, RNG> t2 = Generator.integer(t1._2);
    assertEquals(Integer.valueOf(-723955400), t2._1);
    Tuple<Integer, RNG> t3 = Generator.integer(t2._2);
    assertEquals(Integer.valueOf(1033096058), t3._1);
    Tuple<Integer, RNG> t4 = Generator.integer(t3._2);
    assertEquals(Integer.valueOf(-1690734402), t4._1);
  }

  @Test
  public void testIntegerLimit() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Integer, RNG> t1 = Generator.integer(rng, 100);
    assertEquals(Integer.valueOf(76), t1._1);
    Tuple<Integer, RNG> t2 = Generator.integer(t1._2, 100);
    assertEquals(Integer.valueOf(0), t2._1);
    Tuple<Integer, RNG> t3 = Generator.integer(t2._2, 100);
    assertEquals(Integer.valueOf(58), t3._1);
  }
}
