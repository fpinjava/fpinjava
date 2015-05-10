package com.fpinjava.propertybasedtesting.exercise17;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.propertybasedtesting.exercise17.Gen;
import com.fpinjava.propertybasedtesting.exercise17.Prop;
import com.fpinjava.propertybasedtesting.exercise17.SGen;

public class PropTest {

  @Test
  public void test() {
    Function<Integer, Boolean> isEven = i -> i % 2 == 0;   
    Prop takeWhileProp = Prop.forAll(SGen.listOf(Gen.choose(-100, 100)), ns -> ns.takeWhile(isEven).forAll(isEven));
    assertEquals("+ OK, passed 100 tests.", takeWhileProp.run());
  }

  @Test
  public void test2() {
    Function<Integer, Boolean> f = x -> x < 0;
    Function<List<Integer>, Boolean> g = ns -> List.concat(ns.takeWhile(f), ns.dropWhile(f)).equals(ns);
    Prop takeWhileProp = Prop.forAll(SGen.listOf(Gen.choose(-100, 100)), g);
    assertEquals("+ OK, passed 100 tests.", takeWhileProp.run());
  }

}
