package com.acme.repository.custom;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

public final class DefaultBulkOperationsTest extends AbstractBulkOperationsTest {

  @Inject
  @Qualifier("default")
  private BulkOperations bulkOperations;

  @Override
  protected BulkOperations bulkOperations() {
    return bulkOperations;
  }

}
