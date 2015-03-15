package com.fpinjava.recursion.exercise04_06;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.CollectionUtilities;
import com.fpinjava.common.Function;

import static com.fpinjava.recursion.exercise04_04.Range.*;

public class ComposeAllTest {

  @Test
  public void testComposeAll() {
    Function<Integer, Integer> add = y -> y + 1;
    assertEquals(Integer.valueOf(500), ComposeAll.composeAll(CollectionUtilities.map(range(0, 500), x -> add)).apply(0));
  }
}
