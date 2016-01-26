package com.fpinjava.io;


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

  static <A, B> IO<B> as(IO<A> a, B b) {
    return a.map(ignore -> b);
  }

  static <A> IO<Boolean> when(boolean b, Supplier<IO<A>> sIoa) {
    return b
        ? as(sIoa.get(), true)
        : unit(false);
  }

  static IO<Nothing> ifElse(boolean b, IO<Nothing> io1, IO<Nothing> io2) {
    return b
        ? io1
        : io2;
  }

  static <A> IO<Nothing> doWhile(IO<A> ioa, Function<A, IO<Boolean>> f) {
    return ioa.flatMap(f::apply)
              .flatMap(ok -> ok
                  ? doWhile(ioa, f)
                  : empty);
  }

  static <A> IO<Nothing> sequence(Stream<IO<A>> stream) {
    return forEach(stream, IO::skip);
  }

  static <A> IO<Nothing> skip(IO<A> a) {
    return as(a, Nothing.instance);
  }

  static <A, B> IO<Nothing> foldM_(Stream<A> s, B z, Function<B, Function<A, IO<B>>> f) {
    return skip(foldM(s, z, f));
  }

  static <A, B> IO<B> foldM(Stream<A> s, B z, Function<B, Function<A, IO<B>>> f) {
    return s.isEmpty()
        ? unit(z)
        : f.apply(z).apply(s.head()._1).flatMap(zz -> foldM(s.tail(), zz, f));
  }

  static <A> IO<Nothing> forEach(Stream<A> s, Function<A, IO<Nothing>> f) {
    return foldM_(s, Nothing.instance, n -> t -> skip(f.apply(t)));
  }

  @SafeVarargs
  static <A> IO<Nothing> sequence(IO<A>... array) {
    return sequence(Stream.of(array));
  }

  static <A, B> IO<B> forever(IO<A> ioa) {
    Supplier<IO<B>> t  = () -> forever(ioa);
    return ioa.flatMap(x -> t.get());
  }
}
