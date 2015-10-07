package com.fpinjava.application.generator;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class Main {

  static Generator<Integer> integers = new Generator<Integer>() {

    Random rand = new Random();

    public Integer next() {
      return rand.nextInt();
    }
  };

  static Generator<Boolean> booleans0 = () -> integers.next() > 0;

  static Generator<Boolean> booleans1 = new Generator<Boolean>() {

    Function<Integer, Boolean> f = x -> x > 0;

    @Override
    public Boolean next() {
      return f.apply(integers.next());
    }
  };

  static Generator<Boolean> booleans = integers.map(x -> x > 0);

  static Generator<Boolean> booleans10 = integers.map(x -> x % 10 == 0);

  //static Generator<Boolean> booleans90 = integers.map(x -> x % 10 != 0);

  static Generator<Tuple<Integer, Integer>> pairs0 = () -> new Tuple<>(integers.next(), integers.next());


  public static void main(String[] args) {
//    Random rand = new Random();
//    List.range(0, 10).map(x -> rand.nextInt()).forEach(System.out::println);
    Generator<List<Integer>> lists = Generator.listGenerator(Generator.intGenerator(), 10);
    List.range(0, 10).map(ignore -> lists.next()).forEach(System.out::println);
  }

  public static <T, U> Generator<Tuple<T, U>> pairs0(Generator<T> gt, Generator<U> gu) {
    return gt.flatMap(t -> () -> new Tuple<>(t, gu.next()));
  }

  public static <T, U> Generator<Tuple<T, T>> pairs00(Generator<T> gt) {
    return gt.flatMap(t -> () -> new Tuple<>(t, gt.next()));
  }

  public static <T, U> Generator<Tuple<T, U>> pairs1(Generator<T> gt, Generator<U> gu) {
    return () -> ((Generator<Tuple<T, U>>) () -> new Tuple<>(gt.next(), gu.next())).next();
  }

  public static <T, U> Generator<Tuple<T, U>> pairs2(Generator<T> gt, Generator<U> gu) {
    return (() -> new Tuple<>(gt.next(), gu.next()));
  }

  public static <T, U> Generator<Tuple<T, U>> pairs(Generator<T> gt, Generator<U> gu) {
    return gt.flatMap(t -> gu.map(u -> new Tuple<>(t, u)));
  }

  public static Generator<Integer> choose(int low, int high) {
    return integers.map(x -> low + x % (high - low));
  }

  public static <T> Generator<T> oneOf(List<T> list) {
    return choose(0, list.length()).map(index -> list.getAt(index).getOrThrow());
  }

  public static <T> Generator<T> unit(T t) {
    return () -> t;
  }

  public static <T> Generator<List<T>> emptyLists() {
    return unit(List.list());
  }

  public static Generator<List<Integer>> nonEmptyLists() {
    return integers.flatMap(h -> lists().map(t -> List.cons(h, t)));
  }

  private static Generator<Boolean> boolean90 = new BooleanGenerator(90);

  public static Generator<List<Integer>> lists() {
    return boolean90.flatMap(empty -> empty
        ? Main.<Integer>emptyLists().map(list -> list)
        : nonEmptyLists().map(list -> list));
  }

  @Test
  public void testConcat_() {
    assertTrue(test(lists(), lists(), 1_000_000, t -> u -> List.concat(t, u).reverse().equals(List.concat(u.reverse(), t.reverse()))).isEmpty());
  }

  public <T> List<T> test(Generator<T> gt, int n, Function<T, Boolean> test) {
    return List.range(0, n).flatMap(ignore -> {
      T t = gt.next();
      return test.apply(t) ? List.list() : List.list(t);
    });
  }

  public <T, U> List<Tuple<T, U>> test(Generator<T> gt, Generator<U> gu, int n, Function<T, Function<U, Boolean>> test) {
    return List.range(0, n).flatMap(ignore -> {
      final T t = gt.next();
      final U u = gu.next();
      return test.apply(t).apply(u) ? List.list() : List.list(new Tuple<>(t, u));
    });
  }

  @Test
  public void testConcat() {
    Generator<List<Integer>> lists = Generator.listGenerator(Generator.intGenerator(), 10);
    assertTrue(test(lists, lists, 1_000_000, t -> u -> List.concat(t, u).reverse().equals(List.concat(u.reverse(), t.reverse()))).isEmpty());
  }

  @Test
  public void testIntGenerator() {
    Generator generator = Generator.intGenerator(0L, x -> x % 2 == 0);
    List.range(0, 100).forEach(i -> {
      System.out.println(generator.next());
    });
  }

  @Test
  public void testBooleanGenerator() {
    Generator generator = Generator.intGenerator(0L);
    List.range(0, 10).forEach(i -> {
      System.out.println("-----");List.range(0, 4).forEach(j -> System.out.println(generator.next()));
    });
  }

  @Test
  public void testIntListGenerator_() {
    Generator generator = Generator.listGenerator_(0L, Generator.intGenerator(0L), 2);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testCharListGenerator_() {
    Generator generator = Generator.listGenerator_(0L, Generator.charGenerator_(0L, x -> x == 9 || x == 13 || x == 10  || (x >= 32 && x < 127)), 2);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testIntListGenerator() {
    Generator generator = Generator.listGenerator(0L, Generator.intGenerator(0L), 2);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testIntListGenerator2() {
    Generator generator = Generator.listGenerator(0L, Generator.intGenerator(0L), 2, x -> x.length() >= 2 && x.length() < 5);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testCharListGenerator() {
    Generator generator = Generator.listGenerator(0L, Generator.charGenerator(0L, x -> x == 9 || x == 13 || x == 10  || (x >= 32 && x < 127)), 2);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testStringGenerator_() {
    Function<Character, Boolean> charFilter = x -> x == 9 || x == 13 || x == 10  || (x >= 32 && x < 127);
    Generator generator = Generator.stringGenerator_(0L, charFilter);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }


  @Test
  public void testStringGenerator0() {
    Generator generator = Generator.stringGenerator(0L);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testStringGenerator1() {
    Function<Character, Boolean> charFilter = x -> x == 9 || x == 13 || x == 10  || (x >= 32 && x < 127);
    Generator generator = Generator.stringGenerator(0L, charFilter);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testStringGenerator2() {
    Function<Character, Boolean> f = x -> (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z');
    Generator generator = Generator.stringGenerator(0L, f);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testChooserGenerator() {
    Generator generator = Generator.chooserGenerator(0L, List.list("Doc", "Dopey", "Bashful", "Grumpy", "Sneezy", "Sleepy", "Happy"));
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
    List.range(0, 100000).map(i -> generator.next()).groupBy(x -> x).values().map(List::length).forEach(System.out::println);
  }

  @Test
  public void testWordGenerator() {
    Generator generator = Generator.wordGenerator(0L, 2, 4);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testDateGenerator() {
    Generator generator = Generator.dateGenerator(0L);
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }

  @Test
  public void testPersonGenerator() {
    Generator generator = Generator.personGenerator();
    List.range(0, 100).forEach(i -> System.out.println(generator.next()));
  }
}
