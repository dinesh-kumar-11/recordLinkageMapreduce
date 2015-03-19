
package org.dinesh.er.config;

import java.util.Collection;
import java.util.List;

import org.dinesh.er.comparators.Comparator;

public interface ErConfiguration {


  /**
   * The probability threshold used to decide whether two records
   * represent the same entity. If the probability is higher than this
   * value, the two records are considered to represent the same
   * entity.
   */
  public double getThreshold();

  /**
   * The probability threshold used to decide whether two records may
   * represent the same entity. If the probability is higher than this
   * value, the two records are considered possible matches. Can be 0,
   * in which case no records are considered possible matches.
   */
  public double getMaybeThreshold();

  /**
   * The set of properties Duke records can have, and their associated
   * cleaners, comparators, and probabilities.
   */
  public List<ErProperty> getProperties();

  /**
   * The properties which are used to identify records, rather than
   * compare them.
   */
  public Collection<ErProperty> getIdentityProperties();

  /**
   * Returns the property with the given name, or null if there is no
   * such property.
   */
  public ErProperty getPropertyByName(String name);

  /**
   * Returns the properties Duke queries for in the Lucene index. This
   * is a subset of getProperties(), and is computed based on the
   * probabilities and the threshold.
   */
  public Collection<ErProperty> getLookupProperties();

  /**
   * Validates the configuration to verify that it makes sense.
   * Rejects configurations that will fail during runtime.
   */
  public void validate();

  /**
   * Sets the threshold.
   * @since 1.1
   */
  public void setThreshold(double threshold);

  /**
   * Returns an exact copy of the configuration.
   * @since 1.1
   */
  public ErConfiguration copy();

  /**
   * Adds a custom comparator.
   * @since 1.3
   */
  public void addCustomComparator(Comparator comparator);

  /**
   * Returns any customized comparators declared using object tags
   * in the config file.
   * @since 1.3
   */
  public List<Comparator> getCustomComparators();
}
