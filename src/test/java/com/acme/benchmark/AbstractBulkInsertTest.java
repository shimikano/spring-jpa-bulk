package com.acme.benchmark;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.slf4j.profiler.Profiler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acme.config.TestConfiguration;
import com.acme.model.Customer;

/**
 * The base benchmark (test) class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public abstract class AbstractBulkInsertTest {

  @Inject
  private IDBI dbi;

  /**
   * Clears the database after every test.
   */
  @After
  public final void clearDatabase() {
    dbi.inTransaction(new TransactionCallback<Void>() {
      @Override
      public Void inTransaction(Handle handle, TransactionStatus status) throws Exception {
        handle.execute("delete from customer");
        return null;
      }
    });
  }

  /**
   * Persists lots of entities and checks the count.
   */
  @Test
  public final void bulkInsert() {
    final int n = 100000;

    List<Customer> entities = createEntities(n);

    Profiler profiler = new Profiler("Bulk insert - " + name());

    profiler.start(n + " entities");
    bulkInsert(entities);
    profiler.stop().print();

    assertThat(getCount()).isEqualTo(n);
  }

  private static List<Customer> createEntities(int n) {
    List<Customer> entities = new ArrayList<>(n);

    for (int i = 1; i <= n; i++) {
      entities.add(new Customer(i));
    }

    return entities;
  }

  protected abstract String name();

  protected abstract void bulkInsert(List<Customer> entities);

  protected abstract long getCount();

}
