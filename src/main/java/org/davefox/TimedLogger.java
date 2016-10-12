/*
 * Copyright (c) 2016  Intellectual Reserve, Inc.  All rights reserved.
 */

package org.davefox;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class for periodically logging out interesting application metrics.
 *
 * @author David Fox
 */
public class TimedLogger implements Runnable {

  public static final int PRINT_PERIOD_IN_SECONDS = 5;
  public static final int INITIAL_DELAY_IN_SECONDS = 4;

  private static final TimedLogger instance = new TimedLogger();

  public static TimedLogger getInstance() {
    return instance;
  }

  private final ScheduledThreadPoolExecutor printTimer;
  private final AtomicInteger busyWorkerThreadCount;
  private final AtomicInteger completedTaskCount;

  private TimedLogger() {
    printTimer = new ScheduledThreadPoolExecutor(1);
    busyWorkerThreadCount = new AtomicInteger(0);
    completedTaskCount = new AtomicInteger(0);
  }

  public void startUp() {
    printTimer.scheduleAtFixedRate(this, INITIAL_DELAY_IN_SECONDS, PRINT_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
  }

  public void workerTaskStarted() {
    busyWorkerThreadCount.incrementAndGet();
  }

  public void workerTaskFinished() {
    busyWorkerThreadCount.decrementAndGet();
    completedTaskCount.incrementAndGet();
  }

  public void run() {
    int busyWorkerThreadCountSnapshot = busyWorkerThreadCount.get();
    int completedTaskCountSnapshot = completedTaskCount.getAndSet(0);
    System.out.println(String.format("seconds=%d busyWorkerThreadCount=%d completedTaskCount=%d", System.currentTimeMillis()/1000, busyWorkerThreadCountSnapshot, completedTaskCountSnapshot));
  }
}