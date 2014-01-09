package com.acme.benchmark.jdbc;

import java.util.List;

import javax.inject.Inject;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.PreparedBatch;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.util.LongMapper;

import com.acme.model.Customer;

public final class JdbiBulkInsertTest extends AbstractJdbcBulkInsertTest {

  @Inject
  private IDBI dbi;

  @Override
  protected String name() {
    return "JDBI";
  }

  @Override
  public void createTable() {
    dbi.inTransaction(new TransactionCallback<Void>() {
      @Override
      public Void inTransaction(Handle handle, TransactionStatus status) {
        handle.execute(CREATE_TABLE);

        return null;
      }
    });
  }

  @Override
  protected void bulkInsert(final List<Customer> entities) {
    dbi.inTransaction(new TransactionCallback<int[]>() {
      @Override
      public int[] inTransaction(Handle handle, TransactionStatus status) {
        PreparedBatch preparedBatch = handle.prepareBatch(INSERT);

        for (Customer entity : entities) {
          preparedBatch.add().bind(0, entity.getId());
        }

        return preparedBatch.execute();
      }
    });
  }

  @Override
  protected long getCount() {
    return dbi.inTransaction(new TransactionCallback<Long>() {
      @Override
      public Long inTransaction(Handle handle, TransactionStatus status) {
        return handle.createQuery(SELECT_COUNT).map(LongMapper.FIRST).first();
      }
    });
  }

}
