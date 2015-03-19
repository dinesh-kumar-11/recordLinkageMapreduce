
package org.dinesh.er.database;


/**
 * A key function produces a blocking key from a record.
 * @since 1.2
 */
public interface KeyFunction {

  public String makeKey(Record record);

}
