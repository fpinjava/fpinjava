package com.fpinjava.errorhandling.exercise06_02;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class VarianceTest {

  @Test
  public void testVariance() {
    List<Double> list = List.list(1.0, 2.0, 3.0, 4.0, 5.0);
    assertEquals("Some(2.0)", Variance.variance.apply(list).toString());
  }

  @Test
  public void testVarianceEmpty() {
    List<Double> list = List.list();
    assertEquals("None", Variance.variance.apply(list).toString());
  }

}
