package org.dinesh.er.exception;
/**
 * Used to signal that something has gone wrong during Duke
 * processing.
 */
public class ErException extends RuntimeException {

  public ErException(String msg) {
    super(msg);
  }
  
  public ErException(String msg, Throwable e) {
    super(msg, e);
  }

  public ErException(Throwable e) {
    super(e);
  }
  
}