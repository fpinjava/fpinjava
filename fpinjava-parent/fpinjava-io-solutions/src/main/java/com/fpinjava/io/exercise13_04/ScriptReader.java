package com.fpinjava.io.exercise13_04;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;

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

  /*
   * In case of failure because not enough entries in script, we will get the error
   * after having processed all present entries. Although the last error is due to
   * an empty script, we prefer to signal "not enough entries in script".
   */
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
