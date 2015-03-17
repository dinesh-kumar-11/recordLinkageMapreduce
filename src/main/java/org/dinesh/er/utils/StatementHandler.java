package org.dinesh.er.utils;
/**
 * Event-handler which receives parsed statements.
 */
public interface StatementHandler {
  public void statement(String subject, String property, String object,
                        boolean literal);
}