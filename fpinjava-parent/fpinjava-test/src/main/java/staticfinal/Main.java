package staticfinal;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;

public class Main {

  public static void main(String[] args) {
    List<Integer> list0 = List.list();
    List<Integer> list1 = List.list(3, 6, 9, 4, 2);
    List<String> list2 = List.list();
    List<String> list3 = List.list("Anny", "Mark", "Eric", "Stephen", "Mary");
    System.out.println(Main.<Integer>max().apply(0).apply(list0));
    System.out.println(Main.<Integer>max().apply(0).apply(list1));
    System.out.println(Main.<String>max().apply("Empty list").apply(list2));
    System.out.println(Main.<String>max().apply("Empty list").apply(list3));
    System.out.println();
    System.out.println(max(0, list0));
    System.out.println(max(0, list1));
    System.out.println(max("Empty list", list2));
    System.out.println(max("Empty list", list3));
  }

public static <A extends Comparable<A>> Function<A, Function<List<A>, A>> max() {
  return x0 -> xs -> xs.isEmpty()
      ? x0
      : xs.foldLeft(xs.head(), x -> y -> x.compareTo(y) < 0 ? x : y);
}

public static <A extends Comparable<A>> A max(A x0, List<A> xs) {
  return xs.isEmpty()
      ? x0
      : xs.foldLeft(xs.head(), x -> y -> x.compareTo(y) < 0 ? x : y);
}
}
