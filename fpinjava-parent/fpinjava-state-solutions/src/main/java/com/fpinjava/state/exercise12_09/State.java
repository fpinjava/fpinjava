package com.fpinjava.state.exercise12_09;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class State<S, A> {

  public final Function<S, Tuple<A, S>> run;

  public State(Function<S, Tuple<A, S>> run) {
    super();
    this.run = run;
  }

  public static <S, A> State<S, A> unit(A a) {
    return new State<>(state -> new Tuple<>(a, state));
  }

  public <B> State<S, B> map(Function<A, B> f) {
    return flatMap(a -> State.unit(f.apply(a)));
  }

  public <B, C> State<S, C> map2(State<S, B> sb, Function<A, Function<B, C>> f) {
    return flatMap(a -> sb.map(b -> f.apply(a).apply(b)));
  }

  public <B> State<S, B> flatMap(Function<A, State<S, B>> f) {
    return new State<>(s -> {
      Tuple<A, S> temp = run.apply(s);
      return f.apply(temp._1).run.apply(temp._2);
    });
  }

  public static <S, A> State<S, List<A>> sequence(List<State<S, A>> fs) {
    return fs.foldRight(State.unit(List.<A>list()), f -> acc -> f.map2(acc, a -> b -> b.cons(a)));
  }

}
