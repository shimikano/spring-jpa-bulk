package com.acme.repository.custom;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

public final class FlushingBulkOperationsTest extends AbstractBulkOperationsTest {

  @Inject
  @Qualifier("flushing")
  private BulkOperations bulkOperations;

  @Override
  protected BulkOperations bulkOperations() {
    return bulkOperations;
  }

}
