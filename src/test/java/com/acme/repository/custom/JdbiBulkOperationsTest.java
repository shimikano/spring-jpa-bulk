package com.acme.repository.custom;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

public final class JdbiBulkOperationsTest extends AbstractBulkOperationsTest {

  @Inject
  @Qualifier("jdbi")
  private BulkOperations bulkOperations;

  @Override
  protected BulkOperations bulkOperations() {
    return bulkOperations;
  }

}
