package com.fpinjava.state;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Nothing;
import com.fpinjava.common.Tuple;

public class State<S, A> {
  
  public final Function<S, Tuple<A, S>> run;

  public State(Function<S, Tuple<A, S>> run) {
    super();
    this.run = run;
  }
  
  public static <S, A> State<S, A> unit(A a) {
    return new State<>(s -> new Tuple<>(a, s));
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

  public static <S> State<S, S> get() {
    return new State<>(s -> new Tuple<>(s, s));
  }

  public static <S, A> State<S, A> getState(Function<S, A> f) {
    return new State<>(s -> new Tuple<>(f.apply(s), s));
  }

  public static <S> State<S, Nothing> set(S s) {
    return new State<>(x -> new Tuple<>(Nothing.instance, s));
  }
      
  public static <S> State<S, Nothing> modify_(Function<S, S> f) {
    return new State<>(s -> new Tuple<>(Nothing.instance, f.apply(s)));
  }

  public static <S> State<S, Nothing> modify(Function<S, S> f) {
    return State.<S>get().flatMap(s -> set(f.apply(s)));
  }

  public A eval(S s) {
    return run.apply(s)._1;
  }

}
