package com.fpinjava.application.generator;


import com.fpinjava.common.List;

public class ChooserGenerator<A> implements Generator<A> {

  private final List<A> list;

  private final IntGenerator intGenerator;

  private final Generator<A> generator;

  protected ChooserGenerator(long seed, List<A> list) {
    this.list = list;
    this.intGenerator = new IntGenerator(seed);
    this.generator = intGenerator.map(x -> Math.abs(x) % list.length()).map(index -> list.getAt(index).getOrThrow());
  }

  protected ChooserGenerator(List<A> list) {
    this.list = list;
    this.intGenerator = new IntGenerator();
    this.generator = intGenerator.map(x -> Math.abs(x) % list.length()).map(index -> list.getAt(index).getOrThrow());
  }

  @Override
  public A next() {
    return generator.next();
  }
}
