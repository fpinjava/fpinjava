package com.fpinjava.actors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {

  @Override
  public Thread newThread(Runnable runnableTask) {
    Thread thread = Executors.defaultThreadFactory().newThread(runnableTask);
    thread.setDaemon(true);
    return thread;
  }
}
