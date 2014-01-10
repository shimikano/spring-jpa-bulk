package com.acme.repository.custom;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

public final class StatelessSessionBulkOperationsTest extends AbstractBulkOperationsTest {

  @Inject
  @Qualifier("statelessSession")
  private BulkOperations bulkOperations;

  @Override
  protected BulkOperations bulkOperations() {
    return bulkOperations;
  }

}
