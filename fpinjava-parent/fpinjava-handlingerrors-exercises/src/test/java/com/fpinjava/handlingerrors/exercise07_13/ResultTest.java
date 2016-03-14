package com.fpinjava.handlingerrors.exercise07_13;

import static org.junit.Assert.*;

import com.fpinjava.common.Function;
import org.junit.Test;

public class ResultTest {

  private static Function<Integer, Function<String, Integer>> parseWithRadix = radix -> string -> Integer.parseInt(string, radix);

  private static Function<char[], Function<Integer, Function<Integer, String>>> valueOf = data -> offset -> count -> String.valueOf(data, offset, count);

  @Test
  public void testLift2() {
    int radix = 16;
    String string = "AEF15DB";
    assertEquals(Result.success(Integer.parseInt(string, radix)), Result.lift2(parseWithRadix).apply(Result.success(radix)).apply(Result.success(string)));
  }

  @Test
  public void testLift3() {
    Result<char[]> data = Result.of("Hello, World!".toCharArray());
    Result<Integer> offset = Result.of(7);
    Result<Integer> count = Result.of(5);
    assertEquals(Result.success("World"), Result.lift3(valueOf).apply(data).apply(offset).apply(count));
  }
}
