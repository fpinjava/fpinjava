package com.fpinjava.application.countdown;


import com.fpinjava.common.List;
import com.fpinjava.common.Nothing;
import com.fpinjava.common.Result;
import com.fpinjava.io.Console;
import com.fpinjava.io.IO;

public class Countdown3 {

  private static IO<Long> getCpuTime() {
    return System::currentTimeMillis;
  }

  private static Result<Integer> read(String s) {
    return Result.of(() -> Integer.valueOf(s)).mapFailure("Invalid numeric format: " + s);
  }

  private static Result<List<Integer>> readNumbers(String s) {
    return List.sequence(List.words(s).map(Countdown3::read));
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

    public static class Simple extends Expr {
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

    public static class Composite extends Expr {
      public final Op op;
      public final Expr l;
      public final Expr r;

      private Composite(Op op, Expr x, Expr y) {
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
            : ns.split().flatMap(t1 -> results(t1._1).flatMap(lx -> results(t1._2).flatMap(ry -> combine(lx, ry))));
  }

  private static List<Solution> combine(Solution lx, Solution ry) {
    return Op.ops.filter(op -> op.valid(lx.value, ry.value)).map(op -> new Solution(Expr.expr(op, lx.expr, ry.expr), apply(op, lx.value, ry.value)));
  }

  private static List<Solution> results_(List<Integer> ns) {
    return ns.isEmpty()
        ? List.list()
        : ns.length() == 1
            ? ns.flatMap(n -> n > 0 ? List.list(new Solution(Expr.expr(n), n)) : List.list())
            : ns.split().flatMap(t1 -> results_(t1._1).flatMap(lx -> results(t1._2).flatMap(ry -> combine_(lx, ry))));
  }

  private static List<Solution> combine_(Solution lx, Solution ry) {
    return Op.ops.filter(op -> op.valid_(lx.value, ry.value)).map(op -> new Solution(Expr.expr(op, lx.expr, ry.expr), apply(op, lx.value, ry.value)));
  }


  private static List<Expr> solutions_(List<Integer> ns, int n) {
    return ns.choices().flatMap(ns_ -> results_(ns_).filter(e -> e.value == n)).map(solution -> solution.expr);
  }

  private static IO<Nothing> display(Result<List<Expr>> res) {
    return getCpuTime().flatMap(t0 -> res.tryIO(exprs -> displayWithStartingTime(exprs, t0), Console::printLine));
  }

  private static IO<Nothing> displayWithStartingTime(List<Expr> exprs, long t0) {
    return IO.ifElse(exprs.isEmpty(), displayNoSolutionIO(t0), displaySolutionsIO(t0, exprs));
  }

  private static IO<Nothing> displaySolutionsIO(long t0, List<Expr> exprs) {
    return getCpuTime().flatMap(t1 -> displayFirstSolutionIO(exprs.headOption(), t0, t1)
        .flatMap(x -> Console.readLine("\nPress return to continue searching..."))
        .flatMap(nothing -> getCpuTime()
            .flatMap(t2 -> exprs.tailOption()
                .tryIO(exs -> getCpuTime().flatMap(t3 -> displaySolutionsWithTime(exs, t0, t1, t2, t3)),
                       ignore -> Console.printLine("There are no more solutions")))));
  }

  private static IO<Nothing> displayFirstSolutionIO(Result<Expr> rexpr, long t0, long t1) {
    return Console.printLine(String.format("\nOne possible solution is %s, found in %s ms.", rexpr.map(Expr::toString).getOrElse("!!No expression!!"), t1 - t0));
  }

  private static IO<Nothing> displayNoSolutionIO(long t0) {
    return getCpuTime().flatMap(t1 -> Console.printLine(String.format("\nThere are no solutions, verified in %s ms.", t1 - t0)));
  }

  private static IO<Nothing> displaySolutionsWithTime(List<Expr> tail, long t0, long t1, long t2, long t3) {
    return Console.printLine(String.format("There were %s solutions in total, found in %s ms.", tail.length() + 1, (t1 - t0) + (t3 - t2)));
  }

  public static void main(String... args) {
    // 6 50 75 25 5 3
    // 298
    doMain().run();
  }

  /**
   * Producing a single IO monad using no imperative code, only by composing other IO monads. As a result,
   * a single IO.run() should be used in the program.
   *
   * @return The resulting program (IO monad)
   */
  private static IO<Nothing> doMain() {
    return Console.printLine("\nCOUNTDOWN NUMBERS GAME SOLVER") // print the first line of the presentation message
                  .flatMap(x -> Console.printLine("-----------------------------\n")) // print the second line
                  .flatMap(x -> Console.print("Enter the given numbers: ")) // print the first prompt
                  .flatMap(Console::readLine) // read the list of numbers in string form
                  .map(Countdown3::readNumbers) // convert it to a result of list of integers
                  .flatMap(ns -> Console.print("Enter the target number: ") // print the second prompt
                                        .flatMap(Console::readLine) // read a number in string form
                                        .map(Countdown3::read) // convert it to result of integer
                                        .map(n -> ns.flatMap(ns_ -> n.map(n_ -> solutions_(ns_, n_))))) // compute the solution to a result of list of Expr
                  .flatMap(Countdown3::display); // print the solution
  }
}
