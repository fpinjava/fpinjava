package com.fpinjava.advancedlisthandling.exercise08_10;

import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testUnzip() throws Exception {
    List<Tuple<Integer, String>> list = List.list(new Tuple<>(1, "one"), new Tuple<>(2, "two"), new Tuple<>(3, "three"));
    assertEquals("[1, 2, 3, NIL]", List.unzip(list)._1.toString());
    assertEquals("[one, two, three, NIL]", List.unzip(list)._2.toString());
  }
}
