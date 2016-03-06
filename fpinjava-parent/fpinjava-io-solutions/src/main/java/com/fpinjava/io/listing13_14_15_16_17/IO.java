package com.fpinjava.io.listing13_14_15_16_17;


import com.fpinjava.common.Function;
import com.fpinjava.common.Nothing;
import com.fpinjava.common.Stream;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.TailCall;

import static com.fpinjava.common.TailCall.ret;
import static com.fpinjava.common.TailCall.sus;

public abstract class IO<A> {

  protected abstract boolean isReturn();
  protected abstract boolean isSuspend();

  private static IO<Nothing> EMPTY = new IO.Suspend<>(() -> Nothing.instance);

  public static IO<Nothing> empty() {
    return EMPTY;
  }

  public A run() {
    return run(this);
  }

  private A run(IO<A> io) {
    return run_(io).eval();
  }

  private TailCall<A> run_(IO<A> io) {
      if (io.isReturn()) {
        return ret(((Return<A>) io).value);
      } else if(io.isSuspend()) {
        return ret(((Suspend<A>) io).resume.get());
      } else {
        Continue<A, A> ct = (Continue<A, A>) io;
        IO<A> sub = ct.sub;
        Function<A, IO<A>> f = ct.f;
        if (sub.isReturn()) {
          return sus(() -> run_(f.apply(((Return<A>) sub).value)));
        } else if (sub.isSuspend()) {
          return sus(() -> run_(f.apply(((Suspend<A>) sub).resume.get())));
        } else {
          Continue<A, A> ct2 = (Continue<A, A>) sub;
          IO<A> sub2 = ct2.sub;
          Function<A, IO<A>> f2 = ct2.f;
          return sus(() -> run_(sub2.flatMap(x -> f2.apply(x).flatMap(f))));
        }
      }
    }

  public <B> IO<B> map(Function<A, B> f) {
    return flatMap(f.andThen(Return::new));
  }

  @SuppressWarnings("unchecked")
  public <B> IO<B> flatMap(Function<A, IO<B>> f) {
    return (IO<B>) new Continue<>(this, f);
  }

  static <A> IO<A> unit(A a) {
    return new IO.Suspend<>(() -> a);
  }

  static <A, B, C> IO<C> map2(IO<A> ioa, IO<B> iob, Function<A, Function<B, C>> f) {
    return ioa.flatMap(t -> iob.map(u -> f.apply(t).apply(u)));
  }

  static <A> IO<Nothing> doWhile(IO<A> iot, Function<A, IO<Boolean>> f) {
    return iot.flatMap(f::apply)
              .flatMap(ok -> ok
                  ? doWhile(iot, f)
                  : EMPTY);
  }

  static <A> IO<Nothing> repeat(int n, IO<A> io) {
    return forEach(Stream.fill(n, () -> io), IO::skip);
  }

  static <A, B> IO<B> forever(IO<A> ioa) {
    Supplier<IO<B>> a  = () -> forever(ioa);
    return ioa.flatMap(x -> a.get());
  }

  static <A, B> IO<B> fold(Stream<A> s, B z, Function<B, Function<A, IO<B>>> f) {
    return s.isEmpty()
        ? unit(z)
        : f.apply(z).apply(s.head()._1).flatMap(zz -> fold(s.tail(), zz, f));
  }

  static <A, B> IO<B> as(IO<A> a, B b) {
    return a.map(ignore -> b);
  }

  static <A> IO<Nothing> skip(IO<A> a) {
    return as(a, Nothing.instance);
  }

  static <A, B> IO<Nothing> fold_(Stream<A> s, B z, Function<B, Function<A, IO<B>>> f) {
    return skip(fold(s, z, f));
  }

  static <A> IO<Nothing> forEach(Stream<A> s, Function<A, IO<Nothing>> f) {
    return fold_(s, Nothing.instance, n -> a -> skip(f.apply(a)));
  }

  static <A> IO<Nothing> sequence(Stream<IO<A>> stream) {
    return forEach(stream, IO::skip);
  }

  @SafeVarargs
  static <A> IO<Nothing> sequence(IO<A>... array) {
    return sequence(Stream.of(array));
  }

  static <A> IO<Boolean> when(boolean b, Supplier<IO<A>> sIoa) {
    return b
        ? as(sIoa.get(), true)
        : unit(false);
  }

  static <A> IO<IORef<A>> ref(A a) {
    return unit(new IORef<>(a));
  }

  final static class IORef<A> {
    private A value;

    public IORef(A a) {
      this.value = a;
    }

    public IO<A> set(A a) {
      value = a;
      return unit(a);
    }

    public IO<A> get() {
      return unit(value);
    }

    public IO<A> modify(Function<A, A> f) {
      return get().flatMap(a -> set(f.apply(a)));
    }
  }

  final static class Return<A> extends IO<A> {
    public final A value;

    protected Return(A value) {
      this.value = value;
    }

    @Override
    public boolean isReturn() {
      return true;
    }

    @Override
    public boolean isSuspend() {
      return false;
    }
  }

  final static class Suspend<A> extends IO<A> {
    public final Supplier<A> resume;

    protected Suspend(Supplier<A> resume) {
      this.resume = resume;
    }

    @Override
    public boolean isReturn() {
      return false;
    }

    @Override
    public boolean isSuspend() {
      return true;
    }
  }

  final static class Continue<A, B> extends IO<A> {
    public final IO<A> sub;
    public final Function<A, IO<B>> f;

    protected Continue(IO<A> sub, Function<A, IO<B>> f) {
      this.sub = sub;
      this.f = f;
    }

    @Override
    public boolean isReturn() {
      return false;
    }

    @Override
    public boolean isSuspend() {
      return false;
    }
  }
}
