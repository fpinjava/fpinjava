package com.fpinjava.trees.exercise10_14;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testBalanceRandom() {
    int testLimitRandom = 150_000;
    List<Integer> randomTestList = SimpleRNG.doubles(testLimitRandom, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * testLimitRandom * 3));
    Tree<Integer> randomTree = randomTestList.foldLeft(Tree.empty(), m -> m::insert);
    Tree<Integer> result = Tree.balance(randomTree);
    assertEquals(randomTree.size(), result.size());
    assertEquals(Tree.log2nlz(result.size()), result.height());
  }

  @Test
  public void testBalanceOrdered() {
    int testLimitOrdered = 15_000; // Too high value will overflow the stack on Tree.insert
    List<Integer> orderedTestList = List.range(0, testLimitOrdered);
    Tree<Integer> orderedTree = orderedTestList.foldLeft(Tree.empty(), m -> m::insert);
    Tree<Integer> result = Tree.balance(orderedTree);
    assertEquals(orderedTree.size(), result.size());
    assertEquals(Tree.log2nlz(result.size()), result.height());
  }
}
