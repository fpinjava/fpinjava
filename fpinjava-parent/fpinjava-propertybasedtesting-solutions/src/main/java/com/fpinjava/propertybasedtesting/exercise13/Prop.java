package com.fpinjava.propertybasedtesting.exercise13;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Option;
import com.fpinjava.common.Stream;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;
import com.fpinjava.state.RNG;
import com.fpinjava.state.SimpleRNG;

public class Prop {

  public final Function<Tuple3<MaxSize, TestCases, RNG>, Result> run;

  public Prop(Function<Tuple3<MaxSize, TestCases, RNG>, Result> run) {
    super();
    this.run = run;
  }

  public static Prop prop2(Function<Tuple<TestCases, RNG>, Result> f) {
    return new Prop(x -> f.apply(new Tuple<>(x._2, x._3)));
  }

  public static <A> Prop forAll(SGen<A> g, Function<A, Boolean> f) {
    return forAll(g::apply, f);
  }

  public static <A> Prop forAll(Function<Integer, Gen<A>> g, Function<A, Boolean> f) {
    Function<Tuple3<MaxSize, TestCases, RNG>, Result> h = tuple3 -> {
      final int max = tuple3._1.value;
      final int n = tuple3._2.value;
      final TestCases casesPerSize = new TestCases((n + (max - 1)) / max);                      // #A
      final Stream<Prop> props = Stream.from(0).take(Math.min(n, max) + 1).map(i -> forAll(g.apply(i), f)); //  #B
      final Prop prop = props.map(p -> new Prop(t3 -> p.run.apply(new Tuple3<>(t3._1, casesPerSize, t3._3)))).toList().reduce(x -> x::and); // #C
      return prop.run.apply(tuple3);
    };
    return new Prop(h);
  }

  public static <A> Prop forAll(Gen<A> as, Function<A, Boolean> f) {
    Function<Tuple<A, Integer>, Result> g = x -> {
      try {
        if (f.apply(x._1)) {
          return new Passed();
        } else {
          return new Falsified(new FailedCase(x._1.toString()),
              new SuccessCount(x._2));
        }
      } catch (Exception e) {
        return new Falsified(new FailedCase(buildMsg(x._1, e)),
            new SuccessCount(x._2));
      }
    };
    Function<Tuple<TestCases, RNG>, Result> h = t -> randomStream(as, t._2)
        .zip(Stream.from(0))
        .take(t._1.value)
        .map(g)
        .find(Result::isFalsified)
        .getOrElse(new Passed());
    return new Prop(x -> h.apply(new Tuple<>(x._2, x._3)));
  }

  public Prop and(Prop p) {
    return new Prop(tuple -> {
      Result result = run.apply(tuple);
      return result.isFalsified() ? result : p.run.apply(tuple);
    });
  }

  public Prop or(Prop p) {
    return new Prop(tuple -> {
      Result result = run.apply(tuple);
      if (result.isFalsified()) {
        Falsified falsified = (Falsified) result;
        return p.tag(falsified.failure).run.apply(tuple);
      } else {
        return result;
      }
    });
  }

  /* This is rather simplistic - in the event of failure, we simply prepend
   * the given message on a newline in front of the existing message.
   */
  public Prop tag(FailedCase msg) {
    return new Prop(tuple -> {
      Result result = run.apply(tuple);
      if (result.isFalsified()) {
        Falsified falsified = (Falsified) result;
        FailedCase failedCase = new FailedCase(msg.value + "\n" + falsified.failure.value);
        return new Falsified(failedCase, falsified.success);
      } else {
        return result;
      }
    });
  }

  /*
   * Produce an infinite random stream from a `Gen` and a starting `RNG`.
   */
  public static <A> Stream<A> randomStream(Gen<A> g, RNG r) {
    return Stream.<A, RNG> unfold(r,
        rng -> Option.some(g.sample.run.apply(rng)));
  }

  public static <A> String buildMsg(A s, Exception e) {
    return String.format(
        "test case: %s\ngenerated an exception: %s\nstack trace: %s\n",
        s, e.getMessage(), List.list(e.getStackTrace()).map(StackTraceElement::toString).mkString("\n"));
  }

  public String run() {
    return run(maxSize(100), testCases(100), new SimpleRNG.Simple(System.currentTimeMillis()));
  }

  public String run(MaxSize maxSize) {
    return run(maxSize, testCases(100), new SimpleRNG.Simple(System.currentTimeMillis()));
  }

  public String run(TestCases testCases) {
    return run(maxSize(100), testCases, new SimpleRNG.Simple(System.currentTimeMillis()));
  }

  public String run(MaxSize maxSize, TestCases testCases) {
    return run(maxSize, testCases, new SimpleRNG.Simple(System.currentTimeMillis()));
  }

  public String run(MaxSize maxSize, TestCases testCases, RNG rng) {
    final Result result = run.apply(new Tuple3<>(maxSize, testCases, rng));
    if (result.isFalsified()) {
      final Falsified falsified = (Falsified) result;
      return String.format("! Falsified after %s passed tests:\n %s", falsified.success, falsified.failure);
    } else {
      return String.format("+ OK, passed %s tests.", testCases);
    }
  }

  public static TestCases testCases(int testCases) {
    return new TestCases(testCases);
  }

  public static class TestCases {

    public final int value;

    public TestCases(int value) {
      super();
      this.value = value;
    }

    public String toString() {
      return Integer.toString(this.value);
    }
  }

  public static abstract class Result {
    protected abstract boolean isFalsified();
  }

  public static class Passed extends Result {
    public boolean isFalsified() {
      return false;
    }
  }

  public static class Falsified extends Result {

    public final FailedCase failure;

    public final SuccessCount success;

    public Falsified(FailedCase failure, SuccessCount success) {
      super();
      this.failure = failure;
      this.success = success;
    }

    public boolean isFalsified() {
      return true;
    }
  }

  public static class SuccessCount {

    public final int value;

    public SuccessCount(int value) {
      super();
      this.value = value;
    }

    public String toString() {
      return Integer.toString(this.value);
    }
  }

  public static class FailedCase {

    public final String value;

    public FailedCase(String value) {
      super();
      this.value = value;
    }

    public String toString() {
      return this.value;
    }
  }

  public static MaxSize maxSize(int maxSize) {
    return new MaxSize(maxSize);
  }

  public static class MaxSize {

    public final int value;

    public MaxSize(int value) {
      super();
      this.value = value;
    }
  }
}
