package com.fpinjava.state.exercise12_11;


import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.exercise12_10.Condition;
import com.fpinjava.state.exercise12_10.StateMachine;
import com.fpinjava.state.exercise12_10.Transition;

public class Atm {

  public static StateMachine<Input, Outcome> createMachine() {

    Condition<Input, Outcome> predicate1 = t -> t.value.isDeposit();
    Transition<Input, Outcome> transition1 = Outcome::add;

    Condition<Input, Outcome> predicate2 = t -> t.value.isWithdraw() && t.value.getAmount().map(w -> w <= t.state.account).getOrElse(false);
    Transition<Input, Outcome> transition2 = Outcome::sub;

    /*
     * Without the commented checking, this condition must come after the previous one in the list
     * With the commented checking, the order is not significant
     */
    Condition<Input, Outcome> predicate3 = t -> t.value.isWithdraw();// && t.value.getAmount().map(w -> w > t.state.account).getOrElse(false);
    Transition<Input, Outcome> transition3 = Outcome::err;

    Condition<Input, Outcome> predicate4 = t -> true;
    Transition<Input, Outcome> transition4 = t -> t.state;

    List<Tuple<Condition<Input, Outcome>, Transition<Input, Outcome>>> transitions = List.list(
        new Tuple<>(predicate1, transition1),
        new Tuple<>(predicate2, transition2),
        new Tuple<>(predicate3, transition3),
        new Tuple<>(predicate4, transition4));

    return new StateMachine<>(transitions);
  }

}
