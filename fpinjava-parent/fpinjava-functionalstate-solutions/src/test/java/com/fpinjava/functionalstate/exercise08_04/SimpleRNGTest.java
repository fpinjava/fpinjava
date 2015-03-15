package com.fpinjava.functionalstate.exercise08_04;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class SimpleRNGTest {

  @Test
  public void testInts() {
    assertEquals(List.list().toString(), SimpleRNG.ints(0, new Simple(1))._1.toString());
    assertEquals(List.list(384748, -1151252339, -549383847, 1612966641).toString(), SimpleRNG.ints(4, new Simple(1))._1.toString());
  }

  @Test
  public void testInts2() {
    assertEquals(List.list().toString(), SimpleRNG.ints2(0, new Simple(1))._1.toString());
    assertEquals(List.list(1612966641, -549383847, -1151252339, 384748).toString(), SimpleRNG.ints2(4, new Simple(1))._1.toString());
  }
  
}
