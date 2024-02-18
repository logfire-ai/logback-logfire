package ai.logfire.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.List;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import ai.logfire.logback.LogfireAppender;
import ai.logfire.logback.LogfireResponse;

public class LogfireAppenderDecorator extends LogfireAppender {
  private Exception exception;
  private LogfireResponse response;
  protected int apiCalls = 0;

  private ReentrantLock flushLock = new ReentrantLock();

  @Override
  protected LogfireResponse callHttpURLConnection(int flushedSize) throws IOException {
    try {
      apiCalls++;
      this.response = super.callHttpURLConnection(flushedSize);

      return this.response;
    } catch (Exception e) {
      this.exception = e;
      throw e;
    }
  }

  @Override
  public void flush() {
    flushLock.lock();
    super.flush();
    flushLock.unlock();
  }

  public void awaitFlushCompletion() {
    try {
      // Wait a bit for possible asyncFlush to be initialized
      Thread.sleep(10);
    } catch (InterruptedException e) {
      // Ignore interruption
    }
    flushLock.lock();
    flushLock.unlock();
  }

  public boolean hasException() {
    return this.exception != null;
  }

  public boolean hasError() {
    return this.response != null && this.response.getError() != null;
  }

  public boolean isOK() {
    return this.response != null && this.response.getStatus() == 202;
  }

  public Exception getException() {
    return exception;
  }

  public LogfireResponse getResponse() {
    return response;
  }
}
