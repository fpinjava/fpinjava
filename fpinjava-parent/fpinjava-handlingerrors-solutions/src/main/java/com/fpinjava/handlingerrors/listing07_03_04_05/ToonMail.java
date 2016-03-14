package com.fpinjava.handlingerrors.listing07_03_04_05;


import com.fpinjava.handlingerrors.exercise07_04.Result;

import java.io.IOException;

public class ToonMail {

  public static void main(String[] args) {
    Map<String, Toon> toons = new Map<String, Toon>()
        .put("Mickey", new Toon("Mickey", "Mouse", "mickey@disney.com"))
        .put("Minnie", new Toon("Minnie", "Mouse"))
        .put("Donald", new Toon("Donald", "Duck", "donald@disney.com"));

    Result<String> result = getName().flatMap(toons::get).flatMap(Toon::getEmail);

    System.out.println(result);

  }

  public static Result<String> getName() {
    return Result.success("Mickey");
    //return Result.failure(new IOException("Input error"));
    //return Result.success("Minnie");
    //return Result.success("Goofy");
  }
}
