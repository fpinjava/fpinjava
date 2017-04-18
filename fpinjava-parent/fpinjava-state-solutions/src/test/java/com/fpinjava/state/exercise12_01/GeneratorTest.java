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
    assertEquals(Integer.valueOf(384748), t1._1);
    Tuple<Integer, RNG> t2 = Generator.integer(t1._2);
    assertEquals(Integer.valueOf(-1155484576), t2._1);
    Tuple<Integer, RNG> t3 = Generator.integer(t2._2);
    assertEquals(Integer.valueOf(-723955400), t3._1);
    Tuple<Integer, RNG> t4 = Generator.integer(t3._2);
    assertEquals(Integer.valueOf(1033096058), t4._1);
  }

  @Test
  public void testIntegerRt() {
    RNG rng = JavaRNG.rng(0);
    assertEquals(Integer.valueOf(384748), Generator.integer(rng)._1);
    assertEquals(Integer.valueOf(384748), Generator.integer(rng)._1);
    assertEquals(Integer.valueOf(384748), Generator.integer(rng)._1);
  }

  @Test
  public void testIntegerLimit() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Integer, RNG> t1 = Generator.integer(rng, 100);
    assertEquals(Integer.valueOf(48), t1._1);
    Tuple<Integer, RNG> t2 = Generator.integer(t1._2, 100);
    assertEquals(Integer.valueOf(76), t2._1);
    Tuple<Integer, RNG> t3 = Generator.integer(t2._2, 100);
    assertEquals(Integer.valueOf(0), t3._1);
  }
}
