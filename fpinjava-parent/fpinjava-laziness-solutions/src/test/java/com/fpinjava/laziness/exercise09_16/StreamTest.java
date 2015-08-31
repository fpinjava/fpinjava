package com.fpinjava.laziness.exercise09_16;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  @Test
  public void testTake() throws Exception {
    List<Integer> list = Stream.from(0).take(100000).toList();
    System.out.println(list.length());
  }

  @Test
  public void testDrop() throws Exception {
    List<Integer> list = Stream.from(0).take(100000).drop(99000).toList();
    System.out.println(list.length());
  }

  @Test
  public void testTakeWhile() throws Exception {
    List<Integer> list = Stream.from(0).takeWhile(x -> x < 100000).toList();
    System.out.println(list.length());
  }
}
