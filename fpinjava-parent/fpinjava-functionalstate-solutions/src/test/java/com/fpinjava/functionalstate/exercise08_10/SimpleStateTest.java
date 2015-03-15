package com.fpinjava.functionalstate.exercise08_10;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

public class SimpleStateTest {

  @Test
  public void testNonNegativeInt() {
    Tuple<Integer, RNG> t1 = SimpleState.nonNegativeInt(new Simple(1));
    assertEquals(Integer.valueOf(384748), t1._1);
    Tuple<Integer, RNG> t2 = SimpleState.nonNegativeInt(t1._2);
    assertEquals(Integer.valueOf(1151252338), t2._1);
  }

  @Test
  public void testDoubleRnd() {
    Tuple<Double, RNG> t1 = SimpleState.doubleRnd(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1);
    Tuple<Double, RNG> t2 = SimpleState.doubleRnd(t1._2);
    assertEquals(Double.valueOf(0.5360936457291245), t2._1);
  }

  @Test
  public void testIntDouble() {
    Tuple<Tuple<Integer, Double>, RNG> t1 = SimpleState.intDouble(new Simple(1));
    assertEquals(Integer.valueOf(384748), t1._1._1);
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._2);
  }

  @Test
  public void testDoubleInt() {
    Tuple<Tuple<Double, Integer>, RNG> t1 = SimpleState.doubleInt(new Simple(1));
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._1);
    assertEquals(Integer.valueOf(384748), t1._1._2);
  }

  @Test
  public void testDouble3() {
    Tuple<Tuple3<Double, Double, Double>, RNG> t1 = SimpleState.double3(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1._1);
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._2);
    assertEquals(Double.valueOf(0.2558267889544368), t1._1._3);
  }

  @Test
  public void testInts() {
    assertEquals(List.list().toString(), SimpleState.ints(0, new Simple(1))._1.toString());
    assertEquals(List.list(384748, -1151252339, -549383847, 1612966641).toString(), SimpleState.ints(4, new Simple(1))._1.toString());
  }

  @Test
  public void testInts2() {
    assertEquals(List.list().toString(), SimpleState.ints2(0, new Simple(1))._1.toString());
    assertEquals(List.list(1612966641, -549383847, -1151252339, 384748).toString(), SimpleState.ints2(4, new Simple(1))._1.toString());
  }
  
  @Test
  public void testNonNegativeEven() {
    Tuple<Double, RNG> t1 = SimpleState.doubleRnd.run.apply(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1);
    Tuple<Double, RNG> t2 = SimpleState.doubleRnd.run.apply(t1._2);
    assertEquals(Double.valueOf(0.5360936457291245), t2._1);
  }

  @Test
  public void testMap2IntDouble() {
    Tuple<Tuple<Integer, Double>, RNG> t1 = SimpleState.randIntDouble.run.apply(new Simple(1));
    assertEquals(Integer.valueOf(384748), t1._1._1);
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._2);
  }

  @Test
  public void testMap2DoubleInt() {
    Tuple<Tuple<Double, Integer>, RNG> t1 = SimpleState.randDoubleInt.run.apply(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1._1);
    assertEquals(Integer.valueOf(-1151252339), t1._1._2);
  }
  
  @Test
  public void testSequence() {
    assertEquals(List.list(1, 2, 3).toString(), 
        SimpleState.sequence(List.list(SimpleState.unit(1), SimpleState.unit(2), SimpleState.unit(3)))
           .run.apply(new Simple(1))._1.toString());
  }

  @Test
  public void testInts3() {
    assertEquals(List.list().toString(), SimpleState.ints(0).run.apply(new Simple(1))._1.toString());
    assertEquals(List.list(384748, -1151252339, -549383847, 1612966641).toString(), SimpleState.ints(4).run.apply(new Simple(1))._1.toString());
  }

  @Test
  public void testNonNegativeLessThan() {
    Tuple<Integer, RNG> t1 = SimpleState.nonNegativeLessThan(10).run.apply(new Simple(1));
    assertEquals(Integer.valueOf(8), t1._1);
    Tuple<Integer, RNG> t2 = SimpleState.nonNegativeLessThan(10).run.apply(t1._2);
    assertEquals(Integer.valueOf(8), t2._1);
    Tuple<Integer, RNG> t3 = SimpleState.nonNegativeLessThan(10).run.apply(t2._2);
    assertEquals(Integer.valueOf(6), t3._1);
    Tuple<Integer, RNG> t4 = SimpleState.nonNegativeLessThan(10).run.apply(t3._2);
    assertEquals(Integer.valueOf(1), t4._1);
    Tuple<Integer, RNG> t5 = SimpleState.nonNegativeLessThan(10).run.apply(t4._2);
    assertEquals(Integer.valueOf(1), t5._1);
  }

  @Test
  public void testNonNegativeEven2() {
    Tuple<Double, RNG> t1 = SimpleState.doubleRnd.run.apply(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1);
    Tuple<Double, RNG> t2 = SimpleState.doubleRnd.run.apply(t1._2);
    assertEquals(Double.valueOf(0.5360936457291245), t2._1);
  }

  @Test
  public void testMap2IntDouble2() {
    Tuple<Tuple<Integer, Double>, RNG> t1 = SimpleState.randIntDouble.run.apply(new Simple(1));
    assertEquals(Integer.valueOf(384748), t1._1._1);
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._2);
  }

  @Test
  public void testMap2DoubleInt2() {
    Tuple<Tuple<Double, Integer>, RNG> t1 = SimpleState.randDoubleInt.run.apply(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1._1);
    assertEquals(Integer.valueOf(-1151252339), t1._1._2);
  }

  @Test
  public void testRollDieBug() {
    assertEquals(Integer.valueOf(0),  SimpleState.rollDieBug.run.apply(new Simple(5))._1);
  }

  @Test
  public void testRollDie() {
    assertEquals(Integer.valueOf(1),  SimpleState.rollDie.run.apply(new Simple(5))._1);
  }

}
