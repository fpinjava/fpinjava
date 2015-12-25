package com.fpinjava.io.listing13_09;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.io.listing13_02.Input;

public class ScriptReader implements Input {

  private final List<String> commands;

  public ScriptReader(List<String> commands) {
    super();
    this.commands = commands;
  }

  public ScriptReader(String... commands) {
    super();
    this.commands = List.list(commands);
  }

  @Override
  public Result<Tuple<String, Input>> readString() {
    return commands.isEmpty()
        ? Result.failure("Not enough entries in script")
        : Result.success(new Tuple<>(commands.headOption().getOrElse(""), new ScriptReader(commands.drop(1))));
  }

  @Override
  public Result<Tuple<Integer, Input>> readInt() {
    try {
      return commands.isEmpty()
          ? Result.failure("Not enough entries in script")
          : Integer.parseInt(commands.headOption().getOrElse("")) >= 0
              ? Result.success(new Tuple<>(Integer.parseInt(commands.headOption().getOrElse("")), new ScriptReader(commands.drop(1))))
              : Result.empty();
    } catch(Exception e) {
      return Result.failure(e);
    }
  }
}
