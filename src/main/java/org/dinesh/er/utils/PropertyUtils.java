
package org.dinesh.er.utils;

import java.util.Properties;

import org.dinesh.er.exception.ErException;

/**
 * Utilities for making Java Properties objects easier to deal with.
 */
public class PropertyUtils {

  /**
   * Used for getting required properties, will throw an exception if
   * the property is not specified.
 * @throws Exception 
   */
  public static String get(Properties props, String name) {
    String value = props.getProperty(name);
    if (value == null)
      throw new ErException("Required property " + name + " not specified");
    return value;
  }

  /**
   * Returns the value of an optional property, if the property is
   * set.  If it is not set defval is returned.
   */
  public static String get(Properties props, String name, String defval) {
    String value = props.getProperty(name);
    if (value == null)
      value = defval;
    return value;
  }

  /**
   * Returns the value of an optional property, if the property is
   * set.  If it is not set defval is returned.
   */
  public static int get(Properties props, String name, int defval) {
    String value = props.getProperty(name);
    if (value == null)
      return defval;
    return Integer.parseInt(value);
  }
  
}