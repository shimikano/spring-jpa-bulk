package com.acme.benchmark.jdbc;

import org.junit.Before;

import com.acme.benchmark.AbstractBulkInsertTest;

/**
 * Base class for implementations operating directly on the SQL level.
 */
public abstract class AbstractJdbcBulkInsertTest extends AbstractBulkInsertTest {

  protected static final String CREATE_TABLE = "create table if not exists customer (id bigint not null, primary key (id))";

  protected static final String INSERT = "insert into customer (id) values (?)";

  protected static final String SELECT_COUNT = "select count(*) from customer";

  @Before
  public abstract void createTable();

}
