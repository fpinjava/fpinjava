package com.fpinjava.functionalstate.exercise08_11;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple3;

public class CandyMachineSimulator {

  public static State<Machine, Tuple3<Boolean, Integer, Integer>> simulateMachine(List<Input> inputs) {
    List<State<Machine, Nothing>> a = inputs.map(i -> State.modify((Machine m) -> {
      switch (i.type()) {
        case COIN:
          return !m.locked
              ? m
              : m.candies == 0
                  ? m
                  : new Machine(false, m.candies, m.coins + 1);
        case TURN:
          return m.locked
              ? m
              : new Machine(true, Math.max(0, m.candies - 1), m.coins);
        default:
          throw new IllegalStateException(String.format("Unhandled type: %s", i.type()));
      }
    }));
    State<Machine, List<Nothing>> b = State.sequence(a);
    State<Machine, Machine> c = b.flatMap(x -> State.get());
    return c.map(x -> new Tuple3<>(x.locked, x.candies, x.coins));
  }

}
