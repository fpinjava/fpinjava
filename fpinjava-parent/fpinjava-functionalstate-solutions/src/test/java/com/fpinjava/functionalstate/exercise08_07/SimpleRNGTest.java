package com.fpinjava.functionalstate.exercise08_07;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class SimpleRNGTest {

  @Test
  public void testSequence() {
    assertEquals(List.list(1, 2, 3).toString(), 
        SimpleRNG.sequence(List.list(SimpleRNG.unit(1), SimpleRNG.unit(2), SimpleRNG.unit(3)))
           .apply(new Simple(1))._1.toString());
  }

  @Test
  public void testInts() {
    assertEquals(List.list().toString(), SimpleRNG.ints(0).apply(new Simple(1))._1.toString());
    assertEquals(List.list(384748, -1151252339, -549383847, 1612966641).toString(), SimpleRNG.ints(4).apply(new Simple(1))._1.toString());
  }

}
