package com.fpinjava.optionaldata.exercise06_11;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionTest {

  private static Function<Integer, Function<String, Integer>> parseWithRadix = radix -> string -> Integer.parseInt(string, radix);

  @Test
  public void testSequence() {
    Function<String, Option<Integer>> parse16 = Option.hlift(parseWithRadix.apply(16));
    List<String> list = List.list("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    assertEquals("Some([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NIL])", Option.sequence(list.map(parse16)).toString());
  }

  @Test
  public void testSequenceError() {
    Function<String, Option<Integer>> parse8 = Option.hlift(parseWithRadix.apply(8));
    List<String> list = List.list("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    assertEquals("None", Option.sequence(list.map(parse8)).toString());
  }
}
