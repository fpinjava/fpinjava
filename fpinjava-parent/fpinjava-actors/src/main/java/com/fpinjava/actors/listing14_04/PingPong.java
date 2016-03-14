package com.fpinjava.actors.listing14_04;


import com.fpinjava.actors.listing14_01.Actor;
import com.fpinjava.actors.listing14_03.AbstractActor;
import com.fpinjava.common.Result;

public class PingPong {

  static class Player extends AbstractActor<Integer> {

    private final String sound;
    private final Actor<Integer> referee;

    public Player(String id, String sound, Actor<Integer> referee) {
      super(id, Actor.Type.SERIAL);
      this.referee = referee;
      this.sound = sound;
    }

    @Override
    public void onReceive(Integer message, Result<Actor<Integer>> sender) {
      System.out.println(sound + " - " + message);
      if (message >= 10) {
        referee.tell(message, sender);
      } else {
        sender.forEachOrFail(actor -> actor.tell(message + 1, self()))
              .forEach(ignore -> referee.tell(message, sender));
      }
    }
  }
}
