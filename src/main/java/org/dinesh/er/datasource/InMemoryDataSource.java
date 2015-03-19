
package org.dinesh.er.datasource;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.dinesh.er.database.DataSource;
import org.dinesh.er.database.Record;
import org.dinesh.er.database.RecordIterator;

/**
 * Data source which can be passed Record objects, and which then
 * returns them.
 * @since 0.4
 */
public class InMemoryDataSource implements DataSource {
  /**
   * The records held by the data source.
   */
  protected Collection<Record> records;

  /**
   * Creates an empty source.
   */
  public InMemoryDataSource() {
    this.records = new ArrayList<Record>();
  }
  
  /**
   * Creates a source populated with the records in the
   * <tt>records</tt> parameter.
   */
  public InMemoryDataSource(Collection<Record> records) {
    this.records = records;
  }

  @Override
  public RecordIterator getRecords() {
    return new DefaultRecordIterator(records.iterator());
  }

  /**
   * Removes all records held by the data source.
   */
  public void clear() {
    records.clear();
  }

  /**
   * Adds a record to the collection held by the source.
   */
  public void add(Record record) {
    records.add(record);
  }
  
  public void setLogger(Logger logger) {
    // there's not really much to log here, so...
  }
}