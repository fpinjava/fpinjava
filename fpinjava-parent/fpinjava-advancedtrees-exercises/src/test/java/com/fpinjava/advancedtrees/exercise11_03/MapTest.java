package com.fpinjava.advancedtrees.exercise11_03;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import org.junit.Test;

import static org.junit.Assert.*;


public class MapTest {

  @Test
  public void testFold() {
    int limit = 50;
    List<Integer> list = List.range(1, limit + 1);
    List<String> expected = list.map(NumbersToEnglish.convertUS);
    Map<Integer, String> map = list.foldRight(Map.<Integer, String>empty(), i -> m -> m.add(i, NumbersToEnglish.convertUS.apply(i)));
    List<String> list2 = map.values();
    assertTrue(expected.equals(list2));
  }
}
