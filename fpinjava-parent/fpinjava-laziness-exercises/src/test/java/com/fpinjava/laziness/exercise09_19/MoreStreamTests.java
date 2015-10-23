package com.fpinjava.laziness.exercise09_19;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoreStreamTests {

  @Test
  public void testHasSubSequenceFalse2() {
    evaluated = List.list();
    Stream<Integer> stream1 =
        Stream.cons(() -> evaluate(1),
            () -> Stream.cons(() -> evaluate(2),
                () -> Stream.cons(() -> evaluate(3),
                    () -> Stream.cons(() -> evaluate(4),
                        () -> Stream.cons(() -> evaluate(5), Stream::<Integer>empty)))));
    Stream<Integer> stream2 =
        Stream.cons(() -> evaluate(4),
            () -> Stream.cons(() -> evaluate(5),
                () -> Stream.cons(() -> evaluate(6), Stream::<Integer>empty)));
    assertFalse(check(stream1, stream2));
  }

  private boolean check(Stream<Integer> stream1, Stream<Integer> stream2) {
    assertEquals("[NIL]", evaluated.toString());
    boolean result = stream1.hasSubSequence(stream2);
    List<Integer> list1 = stream1.toList();
    assertEquals("[1, 2, 3, 4, 5, NIL]", list1.toString());
    List<Integer> list2 = stream2.toList();
    assertEquals("[4, 5, 6, NIL]", list2.toString());
    return result;
  }

  @Test
  public void testHasSubSequenceFalse3() {
    evaluated = List.list();
    Stream<Integer> stream1 = stream(1, 5);
    Stream<Integer> stream2 = stream(4, 3);
    assertFalse(check(stream1, stream2));
  }

  @Test
  public void testLongDrop() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.drop(200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongDropWhile() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.dropWhile(x -> x < 200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongTake() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.take(500_000).drop(200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongTakeViaUnfold() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.takeViaUnfold(500_000).drop(200_000).takeViaUnfold(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongTakeWhile() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.takeWhile_(x -> x < 500_000).drop(200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongTakeWhileViaFoldRight() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.takeWhile(x -> x < 500_000).drop(200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongTakeWhileViaUnfold() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.takeWhileViaUnfold(x -> x < 500_000).drop(200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongMap() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.map(x -> x * 2).drop(200_000).take(5);
    assertEquals("[400000, 400002, 400004, 400006, 400008, NIL]", result.toList().toString());
  }

  @Test
  public void testLongMapViaUnfold() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.mapViaUnfold(x -> x * 2).drop(200_000).take(5);
    assertEquals("[400000, 400002, 400004, 400006, 400008, NIL]", result.toList().toString());
  }

  @Test
  public void testLongExistsTrue() {
    Stream<Integer> stream1 = Stream.from(0);
    assertTrue(stream1.exists(x -> x > 200_000));
  }

  @Test
  public void testLongExistsFalse() {
    Stream<Integer> stream1 = Stream.from(0).take(200_000);
    assertFalse(stream1.exists(x -> x < 0));
  }

  @Test
  public void testLongForAllFalse() {
    Stream<Integer> stream1 = Stream.from(0);
    assertFalse(stream1.forAll(x -> x > 200_000));
  }

  @Test
  public void testLongForAllTrue() {
    Stream<Integer> stream1 = Stream.from(0).take(200_000);
    assertTrue(stream1.forAll(x -> x >= 0));
  }

  @Test
  public void testLongFilter() {
    Stream<Integer> stream1 = Stream.from(0).take(200_000);
    Stream<Integer> result = stream1.map(x -> x * 2).filter(x -> x % 3 == 0).drop(1000).take(5);
    assertEquals("[6000, 6006, 6012, 6018, 6024, NIL]", result.toList().toString());
  }

  @Test
  public void testLongFilter2() {
    Stream<Integer> stream1 = Stream.from(0).drop(200_000).take(100_000);
    Stream<Integer> result = stream1.filter(prime).take(100);
    assertEquals("[200003, 200009, 200017, 200023, 200029, 200033, 200041, 200063, 200087, 200117, 200131, 200153, 200159, 200171, " +
        "200177, 200183, 200191, 200201, 200227, 200231, 200237, 200257, 200273, 200293, 200297, 200323, 200329, 200341, 200351, " +
        "200357, 200363, 200371, 200381, 200383, 200401, 200407, 200437, 200443, 200461, 200467, 200483, 200513, 200569, 200573, " +
        "200579, 200587, 200591, 200597, 200609, 200639, 200657, 200671, 200689, 200699, 200713, 200723, 200731, 200771, 200779, " +
        "200789, 200797, 200807, 200843, 200861, 200867, 200869, 200881, 200891, 200899, 200903, 200909, 200927, 200929, 200971, " +
        "200983, 200987, 200989, 201007, 201011, 201031, 201037, 201049, 201073, 201101, 201107, 201119, 201121, 201139, 201151, " +
        "201163, 201167, 201193, 201203, 201209, 201211, 201233, 201247, 201251, 201281, 201287, NIL]", result.toList().toString());
  }

  @Test
  public void testLongFilter3() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.filter(x -> x > 200_000).take(5);
    assertEquals("[200001, 200002, 200003, 200004, 200005, NIL]", result.toList().toString());
  }

  private List<Integer> evaluated;

  private int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

  public Stream<Integer> stream(int start, int length) {
    return Stream.from(() -> evaluate(start)).take(length);
  }

  private static class PrimeComputer {
    boolean[] primes = new boolean[1_000_000];
    PrimeComputer() {
      Arrays.fill(primes,true);
      primes[0] = false;
      primes[1] = false;
      for(int i = 2; i < primes.length; i++) {
        if(primes[i]) {
          for(int j = 2; i * j < primes.length; j++) {
            primes[i*j] = false;
          }
        }
      }
    }

    boolean prime(int n) {
      return primes[n];
    }
  }

  private PrimeComputer primeComputer = new PrimeComputer();

  private Function<Integer, Boolean> prime = n -> primeComputer.prime(n);
}
