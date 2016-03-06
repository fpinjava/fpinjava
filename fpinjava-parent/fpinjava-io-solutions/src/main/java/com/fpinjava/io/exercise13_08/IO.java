package com.fpinjava.io.exercise13_08;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Nothing;
import com.fpinjava.common.Stream;
import com.fpinjava.common.Supplier;

public interface IO<A> {

  IO<Nothing> empty = () -> Nothing.instance;

  A run();

  default <B> IO<B> map(Function<A, B> f) {
    return () -> f.apply(this.run());
  }

  default <B> IO<B> flatMap(Function<A, IO<B>> f) {
    return () -> f.apply(this.run()).run();
  }

  static <B> IO<B> unit(B b) {
    return () -> b;
  }

  static <A, B, C> IO<C> map2(IO<A> ioa, IO<B> iob, Function<A, Function<B, C>> f) {
    return ioa.flatMap(a -> iob.map(b -> f.apply(a).apply(b)));
  }

  static <A> IO<List<A>> repeat(int n, IO<A> io) {
    return Stream.fill(n, () -> io).foldRight(() -> unit(List.list()), ioa -> sioLa -> map2(ioa, sioLa.get(), a -> la -> List.cons(a, la)));
  }

  static <A> IO<List<A>> repeat_(int n, IO<A> io) {
    Stream<IO<A>> stream = Stream.fill(n, () -> io);
    Function<A, Function<List<A>, List<A>>> f = a -> la -> List.cons(a, la);
    Function<IO<A>, Function<Supplier<IO<List<A>>>, IO<List<A>>>> g = ioa -> sioLa -> map2(ioa, sioLa.get(), f);
    Supplier<IO<List<A>>> z = () -> unit(List.list());
    return stream.foldRight(z, g);
  }
}
