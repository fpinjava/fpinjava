package com.fpinjava.actors.listing14_05;


import com.fpinjava.common.Result;

import java.util.concurrent.Semaphore;

public class PingPong {

  private static final Semaphore semaphore = new Semaphore(1); // <1>

  public static void main(String... args) throws InterruptedException {
    Actor<Integer> referee =
        new AbstractActor<Integer>("Referee", Actor.Type.SERIAL) {

          @Override
          public void onReceive(Integer message, Result<Actor<Integer>> sender) {
            System.out.println("Game ended after " + message + " shots");
            semaphore.release();
          }
        };

    Actor<Integer> player1 = new Player("Player1", "Ping", referee);
    Actor<Integer> player2 = new Player("Player2", "Pong", referee);

    semaphore.acquire();
    player1.tell(1, Result.success(player2));
    semaphore.acquire();
  }

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
