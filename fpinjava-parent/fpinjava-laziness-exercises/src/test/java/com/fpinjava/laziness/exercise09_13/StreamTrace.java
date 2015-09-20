package com.fpinjava.laziness.exercise09_13;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;

import static org.junit.Assert.assertEquals;

public class StreamTrace {

  private static int evaluate(int n) {
    return n;
  }

  private static Stream<Integer> stream =
      Stream.cons(() -> evaluate(1),
          Stream.cons(() -> evaluate(2),
              Stream.cons(() -> evaluate(3),
                  Stream.cons(() -> evaluate(4),
                      Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));

  private static Function<Integer, Integer> f = x -> {
    System.out.println("Mapping f " + x);
    return x * 3;
  };

  private static Function<Integer, Boolean> p = x -> {
    System.out.println("Filtering " + x);
    return x % 2 == 0;
  };

  public static void main(String... args) {
    List<Integer> list = List.list(1, 2, 3, 4, 5).map(f).filter(p);
    System.out.println(list);
    System.out.println();
    Stream<Integer> result = stream.map(f).filter(p);
    System.out.println(result.toList());
  }
}
