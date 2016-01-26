package com.fpinjava.application.countdown;


import com.fpinjava.common.Executable;
import com.fpinjava.common.List;
import com.fpinjava.common.Nothing;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.io.Console;
import com.fpinjava.io.IO;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Functional Java implementation based upon fpinjava with several implementation of main
 * using read and readNumbers handling NumberFormatException (among others) and returning Result.
 */
public class Countdown1 {

  private static Result<Integer> read(String s) {
    return Result.of(() -> Integer.valueOf(s));
  }

  private static Result<List<Integer>> readNumbers(String s) {
    return List.sequence(List.words(s).map(Countdown1::read));
  }

  private static abstract class Op {
    abstract boolean valid(int x, int y);
    abstract boolean valid_(int x, int y);
    abstract int apply(int x, int y);
    public static Op add = new Add();
    public static Op sub = new Sub();
    public static Op mul = new Mul();
    public static Op div = new Div();
    public static List<Op> ops = List.list(add, sub, mul, div);
  }

  private static class Add extends Op {
    @Override
    boolean valid(int x, int y) {
      return true;
    }

    @Override
    boolean valid_(int x, int y) {
      return x <= y;
    }

    @Override
    int apply(int x, int y) {
      return x + y;
    }

    @Override
    public String toString() {
      return "+";
    }
  }

  private static class Sub extends Op {
    @Override
    boolean valid(int x, int y) {
      return x > y;
    }

    @Override
    boolean valid_(int x, int y) {
      return x > y;
    }

    @Override
    int apply(int x, int y) {
      return x - y;
    }

    @Override
    public String toString() {
      return "-";
    }
  }

  private static class Mul extends Op {
    @Override
    boolean valid(int x, int y) {
      return true;
    }

    @Override
    boolean valid_(int x, int y) {
      return x != 1 && y != 1 && x <= y;
    }

    @Override
    int apply(int x, int y) {
      return x * y;
    }

    @Override
    public String toString() {
      return "*";
    }
  }

  private static class Div extends Op {
    @Override
    boolean valid(int x, int y) {
      return x % y == 0;
    }

    @Override
    boolean valid_(int x, int y) {
      return y != 1 && x % y == 0;
    }

    @Override
    int apply(int x, int y) {
      return x / y;
    }

    @Override
    public String toString() {
      return "/";
    }
  }

  private static int apply(Op op, int x, int y) {
    return op.apply(x, y);
  }

  private static boolean valid(Op op, int x, int y) {
    return op.valid(x, y);
  }

  private static abstract class Expr {

    public abstract List<Integer> values();
    public abstract List<Integer> eval();

    public static Expr expr(int n) {
      return new Simple(n);
    }

    public static Expr expr(Op op, Expr expr1, Expr expr2) {
      return new Composite(op, expr1, expr2);
    }

    public static class Simple extends Countdown1.Expr {
      public final int value;

      private Simple(int value) {
        this.value = value;
      }

      @Override
      public List<Integer> values() {
        return List.list(value);
      }

      @Override
      public List<Integer> eval() {
        return value > 0 ? List.list(value) : List.list();
      }

      @Override
      public String toString() {
        return Integer.toString(value);
      }
    }

    public static class Composite extends Countdown1.Expr {
      public final Op op;
      public final Countdown1.Expr l;
      public final Countdown1.Expr r;

      private Composite(Op op, Countdown1.Expr x, Countdown1.Expr y) {
        this.op = op;
        this.l = x;
        this.r = y;
      }

      @Override
      public List<Integer> values() {
        return l.values().concat(r.values());
      }

      @Override
      public String toString() {
        return String.format("(%s %s %s)", l, op, r);
      }

      /*
       * This is an example of why for truly optional data, an empty list is often better than an Optional
       */
      @Override
      public List<Integer> eval() {
        return l.eval().flatMap(x -> r.eval().flatMap(y -> valid(op, x, y) ? List.list(apply(op, x, y)) : List.list()));
      }
    }
  }

  // Combinatorial functions
  // -----------------------

  private static <A> List<List<A>> subs(List<A> list) {
    return list.subLists();
  }

  private static <A> List<List<A>> interleave(A a, List<A> list) {
    return list.interleave(a);
  }

  private static <A> List<List<A>> perms(List<A> list) {
    return list.perms();
  }

  private static <A> List<List<A>> choices(List<A> list) {
    return list.choices();
  }

  // Formalising the problem
  // -----------------------

  private static boolean solution(Expr e, List<Integer> ns, int n) {
    return ns.choices().elem(e.values()) && e.eval().equals(List.list(n));
  }

  // Brute force solution
  // --------------------

  private static <A> List<Tuple<List<A>, List<A>>> split(List<A> list) {
    return list.split();

  }

  private static List<Expr> exprs(List<Integer> list) {
    return list.isEmpty()
        ? List.list()
        : list.length() == 1
            ? List.list(new Expr.Simple(list.headOption().getOrElse(-1))) // default value will never be used
            : split(list).flatMap(t -> exprs(t._1).flatMap(l -> exprs(t._2).flatMap(r -> combine(l, r))));
  }

  private static List<Expr> combine(Expr expr1, Expr expr2) {
    return Op.ops.map(op -> Expr.expr(op, expr1, expr2));
  }

  private static List<Expr> solutions(List<Integer> list, int n) {
    return choices(list).flatMap(list2 -> exprs(list2).filter(e -> e.eval().equals(List.list(n))));
  }


  // Combining generation and evaluation
  // -----------------------------------

  static class Solution {
    public final Expr expr;
    public final int value;

    Solution(Expr expr, int value) {
      this.expr = expr;
      this.value = value;
    }
  }

  private static List<Solution> results(List<Integer> ns) {
    return ns.isEmpty()
        ? List.list()
        : ns.length() == 1
            ? ns.flatMap(n -> n > 0 ? List.list(new Solution(Expr.expr(n), n)) : List.list())
            : split(ns).flatMap(t1 -> results(t1._1).flatMap(lx -> results(t1._2).flatMap(ry -> combine(lx, ry))));
  }

  private static List<Solution> combine(Solution lx, Solution ry) {
    return Op.ops.filter(op -> op.valid(lx.value, ry.value)).map(op -> new Solution(Expr.expr(op, lx.expr, ry.expr), apply(op, lx.value, ry.value)));
  }

  private static List<Expr> solution_(List<Integer> ns, int n) {
    return choices(ns).flatMap(ns2 -> results(ns2).filter(solution -> solution.value == n).map(solution -> solution.expr));
  }

  private static List<Solution> results_(List<Integer> ns) {
    return ns.isEmpty()
        ? List.list()
        : ns.length() == 1
            ? ns.flatMap(n -> n > 0 ? List.list(new Solution(Expr.expr(n), n)) : List.list())
            : split(ns).flatMap(t1 -> results_(t1._1).flatMap(lx -> results(t1._2).flatMap(ry -> combine_(lx, ry))));
  }

  private static List<Solution> combine_(Solution lx, Solution ry) {
    return Op.ops.filter(op -> op.valid_(lx.value, ry.value)).map(op -> new Solution(Expr.expr(op, lx.expr, ry.expr), apply(op, lx.value, ry.value)));
  }

  private static List<Expr> solutions_(List<Integer> ns, int n) {
    return choices(ns).flatMap(ns_ -> results_(ns_).filter(e -> e.value == n)).map(solution -> solution.expr);
  }

  private static String showTime(long l) {
    return String.format(" in %s ms.", l);
  }

  private static IO<Nothing> sayHello() {
    return Console.printLine("Enter your name: ")
                  .flatMap(Console::readLine)
                  .map(Countdown1::buildMessage)
                  .flatMap(Console::printLine);
  }

  private static String buildMessage(String s) {
    return s;
  }

  public static void main(String... args) {
    solutions(List.list(2, 3, 4, 6), 18).forEach(System.out::println);
    IO program = main1_();
    program.run();
    main2_();
    Executable program3 = main3_();
    program3.exec();
    Executable program4 = main4_();
    program4.exec();
    Executable program5 = main5_();
    program5.exec();
  }

  /**
   * Using the IO monad. This version returns an instance of the IO monad that can be run or composed with other instances.
   * The benefit is that it can be composed using many methods such as map, flatMap, map2, repeat, when, doWhile, forever, etc.
   * @return An instance of the IO monad that can be run
   */
  private static IO<Nothing> main0_() {
    return Console.printLine("\nCOUNTDOWN NUMBERS GAME SOLVER") // print the first line of the presentation message
                  .flatMap(x -> Console.printLine("-----------------------------\n")) // print the second line
                  .flatMap(x -> Console.print("Enter the given numbers: ")) // print the first prompt
                  .flatMap(Console::readLine) // read the list of numbers in string form
                  .map(Countdown1::readNumbers) // convert it to a result of list of integers
                  .flatMap(ns -> Console.print("Enter the target number: ") // print the second prompt
                                        .flatMap(Console::readLine) // read a number in string form
                                        .map(Countdown1::read) // convert it to result of integer
                                        .map(n -> ns.flatMap(ns_ -> n.map(n_ -> solutions_(ns_, n_))))) // compute the solution to a result of list of Expr
                  .flatMap(Console::printLine); // print the solution
  }

  /**
   * A decomposed version of using the IO monad, showing how various step of the computation are written in monadic
   * form and then composed. Not that each intermediate IO monad could be run separately.
   *
   * @return An instance of the IO monad that can be run
   */
  private static IO<Nothing> main1_() {
    IO<Nothing> title = Console.printLine("\nCOUNTDOWN NUMBERS GAME SOLVER") // print the first line of the presentation message
                               .flatMap(x -> Console.printLine("-----------------------------\n"));

    IO<Result<List<Integer>>> numbers = Console.print("Enter the given numbers: ") // print the first prompt
                                       .flatMap(Console::readLine) // read the list of numbers in string form
                                       .map(Countdown1::readNumbers); // convert it to a result of list of integers

    IO<Result<Integer>> number = Console.print("Enter the target number: ") // print the second prompt
                                .flatMap(Console::readLine) // read a number in string form
                                .map(Countdown1::read); // convert it to result of integer

    IO<Result<List<Expr>>> exprs = numbers.flatMap(ns -> number.map(n -> ns.flatMap(ns_ -> n.map(n_ -> solutions_(ns_, n_))))); // compute the solution to a result of list of Expr

    return title.flatMap(x -> exprs.flatMap(Console::printLine)); // print the solution
  }

  /**
   * Using imperative Java. This may be composed through imperative techniques such as calling in sequence,
   * conditional execution (if...else, switch...case) and loops.
   */
  public static void main2_() {
    System.out.println("\nCOUNTDOWN NUMBERS GAME SOLVER");
    System.out.println("-----------------------------\n");
    Result<List<Integer>> ns = Console_.readLine("Enter the given numbers: ").flatMap(Countdown1::readNumbers);
    Result<Integer> n = Console_.readLine("Enter the target number: ").flatMap(Countdown1::read);
    Result<List<Expr>> result = ns.flatMap(ns_ -> n.map(n_ -> solutions_(ns_, n_)));
    result.forEachOrFail(System.out::println).forEach(System.out::println);
  }

  /**
   * Using imperative Jav a wrapped in an executable. This is exactly the same as the imperative version but
   * allows producing a result as data (the executable) instead of an effect. This is more functional and
   * allows passing the result to another program that will execute it.
   *
   * @return an Executable containing imperative Java
   */
  public static Executable main3_() {
    return () -> {
      System.out.println("\nCOUNTDOWN NUMBERS GAME SOLVER");
      System.out.println("-----------------------------\n");
      Result<String> ns = Console_.readLine("Enter the given numbers: ");
      Result<String> n = Console_.readLine("Enter the target number: ");
      Result<List<Expr>> result = ns.flatMap(Countdown1::readNumbers).flatMap(ns_ -> n.flatMap(Countdown1::read).map(n_ -> solutions_(ns_, n_)));
      result.forEachOrFail(System.out::println).forEach(System.out::println);
    };
  }

  /**
   * Composing the imperative instructions in a functional way, except for instructions returning void.
   * No real benefit since this is internal to the method and not visible from outside. Just a little more
   * complicated to write and read.
   *
   * @return and Executable containing a mix of imperative and functional code
   */
  public static Executable main4_() {
    return () -> {
      System.out.println("\nCOUNTDOWN NUMBERS GAME SOLVER");
      System.out.println("-----------------------------\n");
      Console_.readLine("Enter the given numbers: ").flatMap(Countdown1::readNumbers)
              .flatMap(ns -> Console_.readLine("Enter the target number: ").flatMap(Countdown1::read).map(n -> solutions_(ns, n)))
              .forEachOrFail(System.out::println)
              .forEach(System.out::println);
    };
  }

  /**
   * Composing the imperative instructions in a functional way, including for instructions returning void.
   * No real benefit since this is internal to the method and not visible from outside. Just even more
   * complicated to write and read.
   *
   * @return and Executable containing functional code
   */
  public static Executable main5_() {
    return () -> {
      Console_.printLine("\nCOUNTDOWN NUMBERS GAME SOLVER")
              .flatMap(x -> Console_.printLine("-----------------------------\n"))
              .flatMap(x -> Console_.readLine("Enter the given numbers: ").flatMap(Countdown1::readNumbers)
                                    .flatMap(ns -> Console_.readLine("Enter the target number: ")
                                                           .flatMap(Countdown1::read).map(n -> solutions_(ns, n))))
              .forEachOrFail(System.out::println)
              .forEach(System.out::println);
    };
  }

  static class Console_ {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static Result<String> readLine() {
      return Result.of(() -> br.readLine(), "Error while reading console");
    }

    public static Result<String> readLine(String prompt) {
      System.out.print(prompt);
      return Result.of(() -> br.readLine(), "Error while reading console");
    }

    public static Result<Nothing> printLine(String s) {
      return Result.of(() -> {
        System.out.println(s);
        return Nothing.instance;
      });
    }
  }
}
