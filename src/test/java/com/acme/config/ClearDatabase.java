package com.acme.config;

import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clears the complete database.
 */
public final class ClearDatabase extends ExternalResource {

  private final Logger log = LoggerFactory.getLogger(ClearDatabase.class);

  private final IDBI dbi;

  public ClearDatabase(IDBI dbi) {
    this.dbi = dbi;
  }

  @Override
  protected void after() {
    log.info("Clearing the database...");

    dbi.inTransaction(new TransactionCallback<Void>() {
      @Override
      public Void inTransaction(Handle handle, TransactionStatus status) throws Exception {
        handle.execute("delete from customer");
        return null;
      }
    });
  }
}
