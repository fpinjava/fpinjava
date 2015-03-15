package com.fpinjava.recursion.exercise04_07;

import static com.fpinjava.recursion.exercise04_04.Range.range;
import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.CollectionUtilities;
import com.fpinjava.common.Function;

public class ComposeAllTest {

  private Function<Integer, Integer> add = y -> y + 1;

  @Test
  public void testComposeAll() {
    assertEquals(Integer.valueOf(500), ComposeAll.composeAll(CollectionUtilities.map(range(0, 500), x -> add)).apply(0));
  }

  @Test
  public void testComposeAllLeft() {
    assertEquals(Integer.valueOf(500), ComposeAll.composeAllViaFoldLeft(CollectionUtilities.map(range(0, 500), x -> add)).apply(0));
  }

  @Test
  public void testComposeAllRight() {
    assertEquals(Integer.valueOf(500), ComposeAll.composeAllViaFoldRight(CollectionUtilities.map(range(0, 500), x -> add)).apply(0));
  }

}
