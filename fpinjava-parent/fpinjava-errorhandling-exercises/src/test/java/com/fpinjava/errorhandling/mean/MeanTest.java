package com.fpinjava.errorhandling.mean;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class MeanTest {

  @Test
  public void testMean() {
    List<Integer> list = List.list(1, 2, 3, 4, 5);
    assertEquals(Double.valueOf(3.0), Mean.mean.apply(list));
  }

  @Test(expected=ArithmeticException.class)
  public void testMeanEmpty() {
    List<Integer> list = List.list();
    Mean.mean.apply(list);
  }

  @Test
  public void testMean_1() {
    List<Integer> list = List.list(1, 2, 3, 4, 5);
    assertEquals(Double.valueOf(3.0), Mean.mean_1.apply(list));
  }

  @Test
  public void testMeanEmpty_1() {
    List<Integer> list = List.list();
    assertEquals(Double.valueOf(Double.NaN), Mean.mean_1.apply(list));
  }

  @Test
  public void testMean_2() {
    List<Integer> list = List.list(1, 2, 3, 4, 5);
    assertEquals(Double.valueOf(3.0), Mean.mean_2.apply(0.0).apply(list));
  }

  @Test
  public void testMeanEmpty_2() {
    List<Integer> list = List.list();
    assertEquals(Double.valueOf(0.0), Mean.mean_2.apply(0.0).apply(list));
  }

  @Test
  public void testMean_3() {
    List<Integer> list = List.list(1, 2, 3, 4, 5);
    assertEquals(Double.valueOf(3.0), Mean.mean_3.apply(list));
  }

  @Test
  public void testMeanEmpty_3() {
    List<Integer> list = List.list();
    assertEquals(Double.valueOf(Double.NaN), Mean.mean_3.apply(list));
  }

  @Test
  public void testMeanOption() {
    List<Integer> list = List.list(1, 2, 3, 4, 5);
    assertEquals("Some(3.0)", Mean.meanOption.apply(list).toString());
  }

  @Test
  public void testMeanEmptyOption() {
    List<Integer> list = List.list();
    assertEquals("None", Mean.meanOption.apply(list).toString());
  }

}
