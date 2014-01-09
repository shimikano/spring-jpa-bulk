package com.acme.benchmark.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.acme.model.Customer;

public final class JdbcTemplateBulkInsertTest extends AbstractJdbcBulkInsertTest {

  @Inject
  private JdbcTemplate template;

  @Inject
  private TransactionTemplate transactionTemplate;

  @Override
  protected String name() {
    return "JdbcTemplate";
  }

  @Override
  public void createTable() {
    transactionTemplate.execute(new TransactionCallback<Void>() {
      @Override
      public Void doInTransaction(TransactionStatus status) {
        template.execute(CREATE_TABLE);

        return null;
      }
    });
  }

  @Override
  protected void bulkInsert(final List<Customer> entities) {
    transactionTemplate.execute(new TransactionCallback<int[]>() {
      @Override
      public int[] doInTransaction(TransactionStatus status) {
        return template.batchUpdate(INSERT, new BatchPreparedStatementSetter() {

          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setLong(1, entities.get(i).getId());
          }

          @Override
          public int getBatchSize() {
            return entities.size();
          }
        });
      }
    });
  }

  @Override
  protected long getCount() {
    return transactionTemplate.execute(new TransactionCallback<Long>() {
      @Override
      public Long doInTransaction(TransactionStatus status) {
        return template.queryForLong(SELECT_COUNT);
      }
    });
  }

}
