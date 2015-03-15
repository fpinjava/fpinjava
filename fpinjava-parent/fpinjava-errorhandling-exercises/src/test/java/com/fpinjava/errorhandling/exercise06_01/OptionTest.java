package com.fpinjava.errorhandling.exercise06_01;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.errorhandling.exercise06_01.Option;

public class OptionTest {

  @Test
  public void testMap() {
    Option<Integer> option = Option.some(2);
    assertEquals("Some(4)", option.map(x -> x * 2).toString());
  }

  @Test
  public void testMapNone() {
    Option<Integer> option = Option.none();
    assertEquals("None", option.map(x -> x * 2).toString());
  }

  @Test
  public void testGetOrElse() {
    Option<Integer> option = Option.some(2);
    assertEquals(Integer.valueOf(4), option.map(x -> x * 2).getOrElse(() -> {throw new RuntimeException();}));
  }

  @Test(expected=RuntimeException.class)
  public void testGetOrElseNone() {
    Option<Integer> option = Option.none();
    option.map(x -> x * 2).getOrElse(() -> {throw new RuntimeException();});
  }

  @Test
  public void testFlatMap() {
    Option<Integer> option = Option.some(2);
    assertEquals("Some(4)", option.flatMap(x -> Option.some(x * 2)).toString());
  }

  @Test
  public void testFlatMapNone() {
    Option<Integer> option = Option.none();
    assertEquals("None", option.flatMap(x -> Option.some(x * 2)).toString());
  }

  @Test
  public void testOrElse() {
    Option<Integer> option = Option.some(2);
    assertEquals("Some(4)", option.map(x -> x * 2).orElse(() -> {throw new RuntimeException();}).toString());
  }

  @Test(expected=RuntimeException.class)
  public void testOrElseNone() {
    Option<Integer> option = Option.none();
    option.map(x -> x * 2).orElse(() -> {throw new RuntimeException();});
  }

  @Test
  public void testFilterTrue() {
    Option<Integer> option = Option.some(2);
    assertEquals("Some(2)", option.filter(x -> x % 2 == 0).toString());
  }

  @Test
  public void testFilterFalse() {
    Option<Integer> option = Option.some(3);
    assertEquals("None", option.filter(x -> x % 2 == 0).toString());
  }

  @Test
  public void testFilterNone() {
    Option<Integer> option = Option.none();
    assertEquals("None", option.filter(x -> x % 2 == 0).toString());
  }

}
