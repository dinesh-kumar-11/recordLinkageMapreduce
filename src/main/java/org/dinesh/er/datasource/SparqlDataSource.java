
package org.dinesh.er.datasource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.dinesh.er.database.Record;
import org.dinesh.er.database.RecordIterator;
import org.dinesh.er.exception.ErException;
import org.dinesh.er.utils.SparqlClient;
import org.dinesh.er.utils.SparqlResult;

public class SparqlDataSource extends ColumnarDataSource {
  private static final int DEFAULT_PAGE_SIZE = 1000;
  private String endpoint;
  private String query;
  protected int pagesize; // protected for test purposes
  /**
   * In triple mode we expect query results to be of the form:
   * (subject, property, value), whereas in normal mode we treat the
   * query as tabular (ie: one row per subject).
   */
  private boolean triple_mode;

  public SparqlDataSource() {
    this.pagesize = DEFAULT_PAGE_SIZE;
    this.triple_mode = true;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  /**
   * If pagesize is set to 0, paging is disabled.
   */
  public void setPageSize(int pagesize) {
    this.pagesize = pagesize;
  }

  public void setTripleMode(boolean triple_mode) {
    this.triple_mode = triple_mode;
  }

  public int getPageSize() {
    return pagesize;
  }

  public boolean getTripleMode() {
    return triple_mode;
  }

  public String getQuery() {
    return query;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public RecordIterator getRecords() {
    verifyProperty(endpoint, "endpoint");
    verifyProperty(query, "query");

    if (triple_mode)
      return new TripleModeIterator();
    else
      return new TabularIterator();
  }

  protected String getSourceName() {
    return "SPARQL";
  }

  /**
   * An extension point so we can control how the query gets executed.
   * This exists for testing purposes, not because we believe it will
   * actually be used for real.
   */
  public SparqlResult runQuery(String endpoint, String query) {
    return SparqlClient.execute(endpoint, query);
  }

  // --- SparqlIterator

  abstract class SparqlIterator extends RecordIterator {
    protected int pageno;
    protected int pagerow;
    protected List<String> variables;
    protected List<String[]> page;
    protected RecordBuilder builder;

    public SparqlIterator() {
      this.builder = new RecordBuilder(SparqlDataSource.this);
      fetchNextPage();
    }

    public boolean hasNext() {
      return pagerow < page.size();
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    protected void fetchNextPage() {
      if (pagesize == 0 && pageno > 0) {
        // paging is turned off, and we've been asked to get the second page
        page = Collections.EMPTY_LIST;
        pagerow = 0;
        pageno++;
        return;
      }

      String thisquery = query;
      if (pagesize != 0) // paging is turned off
        thisquery += (" limit " + pagesize + " offset " + (pageno * pagesize));

      if (logger != null)
        logger.debug("SPARQL query: " + thisquery);

      SparqlResult result = runQuery(endpoint, thisquery);
      variables = result.getVariables();
      page = result.getRows();

      if (triple_mode && !page.isEmpty() && page.get(0).length != 3)
        throw new ErException("In triple mode SPARQL queries must " +
                                      "produce exactly three columns!");

      if (logger != null)
        logger.debug("SPARQL result rows: " + page.size());

      pagerow = 0;
      pageno++;
    }

    protected void addValue(int valueix, Column col) {
      if (col == null)
        return;

      String value = page.get(pagerow)[valueix];
      builder.addValue(col, value);
    }
  }

  class TripleModeIterator extends SparqlIterator {

    public Record next() {
      String resource = page.get(pagerow)[0];

      Collection<Column> cols = columns.get("?uri");
      if (cols == null)
        throw new ErException("No '?uri' column. It's required in triple mode");
      Column uricol = cols.iterator().next();

      builder.newRecord();
      builder.setValue(uricol, resource);

      while (pagerow < page.size() && resource.equals(page.get(pagerow)[0])) {
        while (pagerow < page.size() && resource.equals(page.get(pagerow)[0])) {
          cols = columns.get(page.get(pagerow)[1]);
          if (cols != null) {
            for (Column col : cols)
              addValue(2, col);
          }

          pagerow++;
        }

        // did we just step off this page?
        if (pagerow >= page.size())
          fetchNextPage();
      }

      return builder.getRecord();
    }
  }

  class TabularIterator extends SparqlIterator {

    public Record next() {
      builder.newRecord();

      for (int colix = 0; colix < variables.size(); colix++) {
        Collection<Column> cols = columns.get(variables.get(colix));
        if (cols != null)
          for (Column col : cols)
            addValue(colix, col);
      }

      pagerow++;
      // do we need to load the next page?
      if (pagerow >= page.size())
        fetchNextPage();

      return builder.getRecord();
    }
  }
}
