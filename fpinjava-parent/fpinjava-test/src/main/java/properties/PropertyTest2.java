package properties;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

import org.junit.contrib.theories.DataPoint;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.InRange;

@RunWith(Theories.class)
public class PropertyTest2 {

  @Theory
  public void testTakeDropInteger(@ForAll(sampleSize = 10) @From(ListIntegerGenerator.class) List<Integer> list,
                                  @ForAll(sampleSize = 10) @InRange(minInt = 0, maxInt = 50) int i) {
    assumeThat(i, lessThan(list.length()));
    assumeThat(i, greaterThanOrEqualTo(0));
    assertEquals(list, List.concat(list.take(i), list.drop(i)));
  }

  /**
   * Not very useful, since is works even if replacing one foldLeft with a
   * foldRight
   */
  @Theory
  public void testFoldLeft(@ForAll(sampleSize = 10) @From(ListIntegerGenerator.class) List<Integer> list1,
                           @ForAll(sampleSize = 10) @From(ListIntegerGenerator.class) List<Integer> list2) {
    Function<Integer, Function<Integer, Integer>> f = a -> b -> a + b;
    int r1 = List.concat(list1, list2).foldLeft(0, f);
    int r2 = list2.foldLeft(list1.foldLeft(0, f), f);
    assertEquals(r1, r2);
  }

  /**
   * A functional method to reverse the arguments of a function. Not that the
   * original function is in curried form.
   * 
   * @param f
   *          The function to transform
   * @return The transfromed function
   */
  public static <T, U, V> Function<U, Function<T, V>> reverseArgs(Function<T, Function<U, V>> f) {
    return t -> u -> f.apply(u).apply(t);
  }

  /**
   * We may also use a function instead of a functional method, but we will have
   * to extensively use type annotation!
   * 
   * @return A function taking a function of two arguments in curried form and
   *         returning an equivalent function withe the arguments swapped.
   */
  public static <T, U, V> Function<Function<T, Function<U, V>>, Function<U, Function<T, V>>> reverseArgs() {
    return f -> t -> u -> f.apply(u).apply(t);
  }
  
  /**
   * In order to prevent using String functions for Integer test or the other way round, we must define aliases
   */
  public static interface IntegerFunction extends Function<List<Integer>, Function<Integer, List<Integer>>> {}

  /**
   * Another alias for a Character function
   */
  public static interface CharacterFunction extends Function<String, Function<Character, String>> {}
 

  @DataPoint
  public static IntegerFunction fi = a -> b -> a.cons(b);
  
  @DataPoint
  public static CharacterFunction fs = a -> b -> a + b;
  
  /**
   * Assert that for any list l and any function f:
   * <ul>
   * <li>foldLeft(z, f, l) = foldRight(reverse(l), z, g)
   * <li>foldLeft(z, f, l) = revers(foldRight(l, z, g))
   * </ul>
   * where f(x, y) = g(y, x).
   * 
   * @param list
   *          The list to test.
   */
  @Theory
  public void testFoldRightIntegers(@ForAll(sampleSize = 100) @From(ListIntegerGenerator.class) List<Integer> list,
                                    IntegerFunction f) {
    //Function<List<Integer>, Function<Integer, List<Integer>>> f = a -> b -> a.cons(b);
    List<Integer> r1 = list.foldLeft(List.list(), f);
    List<Integer> r2 = list.reverse().foldRight(List.list(), reverseArgs(f));
    /*
     * Instead of using the functional method to reverse the arguments, we may
     * use the function returned by the reversArgs() method. There is absolutely
     * no interest in doing this, except for fun.
     */
    Function<Integer, Function<List<Integer>, List<Integer>>> fr = PropertyTest2.<List<Integer>, Integer, List<Integer>> reverseArgs().apply(f);
    List<Integer> r3 = list.foldRight(List.list(), fr).reverse();
    assertEquals(r1, r2);
    assertEquals(r1, r3);
  }

  @Theory
  public void testFoldRightString(@ForAll(sampleSize = 100) @From(ListCharacterGenerator.class) List<Character> list,
                                  CharacterFunction f) {
    //Function<String, Function<String, String>> f = a -> b -> a + b;
    String r1 = list.foldLeft("", f);
    String r2 = list.reverse().foldRight("", reverseArgs(f));
    /*
     * Instead of using the functional method to reverse the arguments, we may
     * use the function returned by the reversArgs() method. There is absolutely
     * no interest in doing this, except for fun.
     */
    Function<Character, Function<String, String>> fr = PropertyTest2.<String, Character, String>reverseArgs().apply(f);
    String r3 = reverse(list.foldRight("", fr));
    assertEquals(r1, r2);
    assertEquals(r1, r3);
  }
  
  public static String reverse(String string) {
    return new StringBuilder(string).reverse().toString();
  }
}
