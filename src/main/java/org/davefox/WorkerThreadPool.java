/*
 * Copyright (c) 2016  Intellectual Reserve, Inc.  All rights reserved.
 */

package org.davefox;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Represents a thread pool for performing work on multiple threads.
 *
 * @author David Fox
 */
public class WorkerThreadPool implements Runnable {

  public static final int MAX_CONCURRENT_THREADS = 25;
  private volatile ThreadPoolExecutor workerPool;

  public static void main(String[] args) {
    TimedLogger.getInstance().startUp();
    new WorkerThreadPool().startUp();
  }

  public void startUp() {
    this.workerPool = new ThreadPoolExecutor(MAX_CONCURRENT_THREADS, MAX_CONCURRENT_THREADS,
        0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    ScheduledThreadPoolExecutor taskInitiator = new ScheduledThreadPoolExecutor(1);
    taskInitiator.scheduleAtFixedRate(this, 0, 5, TimeUnit.MILLISECONDS);
  }

  public void run() {
    this.workerPool.submit(new WorkerTask());
  }
}
