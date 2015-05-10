package com.fpinjava.propertybasedtesting.exercise16;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.parallelism.Par;

public class PropTest {

  @Test
  public void test() {

    Prop forkProp = Prop.forAllPar(Gen.PINT2, i -> Prop.equal(Par.fork(() -> i), i)).tag(new Prop.FailedCase("fork"));
    System.out.println(forkProp.run());
  }

}
