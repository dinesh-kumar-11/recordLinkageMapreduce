
package org.dinesh.er.datasource;

import java.util.Iterator;

import org.dinesh.er.database.Record;
import org.dinesh.er.database.RecordIterator;

public class DefaultRecordIterator extends RecordIterator {
  private Iterator<Record> it;
  
  public DefaultRecordIterator(Iterator<Record> it) {
    this.it = it;
  }

  public boolean hasNext() {
    return it.hasNext();
  }

  public Record next() {
    return it.next();
  }  
}