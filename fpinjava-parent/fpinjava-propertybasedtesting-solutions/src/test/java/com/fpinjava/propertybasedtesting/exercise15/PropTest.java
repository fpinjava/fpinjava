package com.fpinjava.propertybasedtesting.exercise15;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.parallelism.Par;

public class PropTest {

  @Test
  public void test1() {
    Prop p = Prop.checkPar(
      Prop.equal (
          Par.map(Par.unit(() -> 1), x -> x + 1),
          Par.unit(() -> 2)
      )
    );
    assertEquals("+ OK, passed 100 tests.", p.run());
  }

  @Test
  public void testPINT() {
    Prop p = Prop.forAllPar(Gen.PINT, n -> Prop.equal(Par.map(n, y -> y), n));
    assertEquals("+ OK, passed 100 tests.", p.run());
  }

  @Test
  public void testPINT2() {
    Prop p = Prop.forAllPar(Gen.PINT2, n -> Prop.equal(Par.map(n, y -> y), n));
    assertEquals("+ OK, passed 100 tests.", p.run());
  }
}
