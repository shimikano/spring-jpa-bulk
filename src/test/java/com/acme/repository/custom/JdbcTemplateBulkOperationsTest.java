package com.acme.repository.custom;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

public final class JdbcTemplateBulkOperationsTest extends AbstractBulkOperationsTest {

  @Inject
  @Qualifier("jdbcTemplate")
  private BulkOperations bulkOperations;

  @Override
  protected BulkOperations bulkOperations() {
    return bulkOperations;
  }

}
