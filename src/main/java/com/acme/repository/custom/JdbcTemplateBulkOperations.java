package com.acme.repository.custom;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.acme.model.Customer;

public final class JdbcTemplateBulkOperations implements BulkOperations {

  private final JdbcTemplate template;

  public JdbcTemplateBulkOperations(JdbcTemplate template) {
    this.template = template;
  }

  @Override
  @Transactional
  public void bulkPersist(final List<Customer> entities) {
    template.batchUpdate("insert into customer (id) values (?)", new BatchPreparedStatementSetter() {

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

}
