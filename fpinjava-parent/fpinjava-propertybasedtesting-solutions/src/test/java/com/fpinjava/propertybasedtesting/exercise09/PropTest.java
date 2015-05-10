package com.fpinjava.propertybasedtesting.exercise09;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;

public class PropTest {

  @Test
  public void testAnd() {
    Gen<Integer> intGen1 = Gen.choose(2000, 3000);
    Gen<Integer> intGen2 = Gen.weighted(new Tuple<>(Gen.choose(-5, 0), 0.1), new Tuple<>(Gen.choose(1, 5), 0.9));
    Gen<List<Integer>> gen = intGen2.listOfN(intGen1);
    Function<List<Integer>, Boolean> f1 = ns -> ns.length() >= 2000 && ns.length() < 3000;
    Function<List<Integer>, Boolean> f2 = ns -> ns.forAll(x -> (x >= -5 && x < 0) || (x >= 1 && x < 5));
    Function<List<Integer>, Boolean> f3 = ns -> ns.filter(x -> x > 0).length() > ns.filter(x -> x < 0).length() * 7;
    Prop prop1 = Prop.forAll(gen, f1);
    Prop prop2 = Prop.forAll(gen, f2);
    Prop prop3 = Prop.forAll(gen, f3);
    Prop all = prop1.and(prop2).and(prop3);
    Prop.Result result = all.run.apply(new Tuple<>(new Prop.TestCases(100), new SimpleRNG.Simple(System.currentTimeMillis())));
    assertFalse(result.isFalsified());
  }

  @Test
  public void testOr() {
    Gen<Integer> intGen1 = Gen.choose(2000, 3000);
    Gen<Integer> intGen2 = Gen.choose(-5, 5);
    Gen<List<Integer>> gen = intGen2.listOfN(intGen1);
    Function<List<Integer>, Boolean> f1 = ns -> ns.length() >= 2000 && ns.length() < 3000;
    Function<List<Integer>, Boolean> f2 = ns -> ns.forAll(x -> x >= -5 && x < 5);
    Function<List<Integer>, Boolean> f3 = ns -> ns.filter(x -> x > 0).length() > ns.filter(x -> x < 0).length() * 7;
    Prop prop1 = Prop.forAll(gen, f1);
    Prop prop2 = Prop.forAll(gen, f2);
    Prop prop3 = Prop.forAll(gen, f3);
    Prop all = prop1.or(prop2).or(prop3);
    Prop.Result result = all.run.apply(new Tuple<>(new Prop.TestCases(100), new SimpleRNG.Simple(System.currentTimeMillis())));
    assertFalse(result.isFalsified());
  }

  @Test
  public void testAnd2() {
    Gen<Integer> intGen1 = Gen.choose(0, 3000);
    Gen<Integer> intGen2 = Gen.choose(-5, 5);
    Gen<List<Integer>> gen = intGen2.listOfN(intGen1);
    Function<List<Integer>, Boolean> f1 = ns -> ns.forAll(x -> x >= -5 && x < 5);
    Function<List<Integer>, Boolean> f2 = ns -> {
      int max = List.max(ns);
      return !ns.exists(x -> x > max);
    };
    Prop prop1 = Prop.forAll(gen, f1);
    Prop prop2 = Prop.forAll(gen, f2);
    Prop all = prop1.and(prop2);
    Prop.Result result = all.run.apply(new Tuple<>(new Prop.TestCases(100), new SimpleRNG.Simple(0)));
    assertTrue(result.isFalsified());
    assertTrue(((Prop.Falsified) result).failure.value.startsWith("test case: [NIL]"));
  }

}
