/*
 * Copyright (c) 2016  Intellectual Reserve, Inc.  All rights reserved.
 */

package org.davefox;

import java.util.Random;

/**
 * A task on a worker thread.
 *
 * @author David Fox
 */
public class WorkerTask implements Runnable {

  public void run() {
    try {
      TimedLogger.getInstance().workerTaskStarted();

      // since this code is just for demo purposes, we sleep between 100 and 200 milliseconds to simulate
      // the delay of some expensive processing
      Thread.sleep(100 + new Random().nextInt(100));
    }
    catch (InterruptedException ignored) {
    }
    finally {
      TimedLogger.getInstance().workerTaskFinished();
    }
  }
}