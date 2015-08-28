package com.fpinjava.optionaldata.exercise06_07;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
