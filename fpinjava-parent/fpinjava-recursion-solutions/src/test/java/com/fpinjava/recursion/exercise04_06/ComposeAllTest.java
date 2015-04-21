package com.fpinjava.recursion.exercise04_06;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

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
    
  @Test
  public void testComposeAll1() {
    Function<String, String> f1 = x -> x + "a";
    Function<String, String> f2 = x -> x + "b";
    Function<String, String> f3 = x -> x + "c";
    Function<String, String> f4 = x -> x + "d";
    Function<String, String> f5 = x -> x + "e";
    List<Function<String, String>> list = Arrays.asList(f1, f2, f3, f4, f5);
    assertEquals("edcba", ComposeAll.composeAll(list).apply(""));
  }
  
  @Test
  public void testComposeAllViaAndThen() {
    Function<String, String> f1 = x -> x + "a";
    Function<String, String> f2 = x -> x + "b";
    Function<String, String> f3 = x -> x + "c";
    Function<String, String> f4 = x -> x + "d";
    Function<String, String> f5 = x -> x + "e";
    List<Function<String, String>> list = Arrays.asList(f1, f2, f3, f4, f5);
    assertEquals("edcba", ComposeAll.composeAllViaAndThen(list).apply(""));
  }
  
  
}
