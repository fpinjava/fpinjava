package staticfinal;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Option;


public class Main2 {

  public static void main(String[] args) {
    List<Integer> list0 = List.list();
    List<Integer> list1 = List.list(3, 6, 9, 4, 2);
    List<String> list2 = List.list();
    List<String> list3 = List.list("Anny", "Mark", "Eric", "Stephen", "Mary");
    System.out.println(Main2.<Integer>max().apply(list0));
    System.out.println(Main2.<Integer>max().apply(list1));
    System.out.println(Main2.<String>max().apply(list2));
    System.out.println(Main2.<String>max().apply(list3));
    System.out.println();
  }

public static <A extends Comparable<A>> Function<List<A>, Option<A>> max() {
  return xs -> xs.isEmpty()
      ? Option.none()
      : Option.some(xs.foldLeft(xs.head(), x -> y -> x.compareTo(y) < 0 ? x : y));
}

}
