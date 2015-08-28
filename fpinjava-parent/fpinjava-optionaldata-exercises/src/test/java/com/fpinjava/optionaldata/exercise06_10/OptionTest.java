package com.fpinjava.optionaldata.exercise06_10;

import static org.junit.Assert.*;

import com.fpinjava.common.Function;
import org.junit.Test;

public class OptionTest {

  private static Function<Integer, Function<String, Integer>> parseWithRadix = radix -> string -> Integer.parseInt(string, radix);

  @Test
  public void testMap2() {
    int radix = 16;
    String string = "AEF15DB";
    assertEquals(Option.some(Integer.parseInt(string, radix)).toString(), Option.map2(Option.some(radix), Option.some(string), parseWithRadix).toString());
  }

}
