
package org.dinesh.er.cleaners;


public class CapitalCleaner implements Cleaner {
  private LowerCaseNormalizeCleaner sub;

  public CapitalCleaner() {
    this.sub = new LowerCaseNormalizeCleaner();
  }

  public String clean(String value) {
    // do basic cleaning
    value = sub.clean(value);
    if (value == null || value.equals(""))
      return "";

    // do our stuff
    int ix = value.indexOf(',');
    if (ix != -1)
      value = value.substring(0, ix);

    return value;
  }  
}