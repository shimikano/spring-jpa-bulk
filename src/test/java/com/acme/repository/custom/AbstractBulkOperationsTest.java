package com.acme.repository.custom;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.util.LongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acme.config.ClearDatabase;
import com.acme.config.TestConfiguration;
import com.acme.model.Customer;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

/**
 * The base benchmark (test) class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public abstract class AbstractBulkOperationsTest {

  private final Logger log = LoggerFactory.getLogger(AbstractBulkOperationsTest.class);

  @Rule
  @Inject
  public ClearDatabase clearDatabase;

  @Rule
  public TestRule benchmarkRun = new BenchmarkRule();

  @Inject
  private IDBI dbi;

  /**
   * Persists lots of entities and checks the count.
   */
  @Test
  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 1)
  public final void bulkInsert() {
    final int n = 500000;

    List<Customer> entities = createEntities(n);

    log.info("Persisting...");
    long start = System.nanoTime();
    bulkOperations().bulkPersist(entities);
    long end = System.nanoTime();
    log.info("Done in " + (end - start) / 1000000.0 + " ms");

    assertThat(count()).isEqualTo(n);
  }

  private long count() {
    return dbi.inTransaction(new TransactionCallback<Long>() {
      @Override
      public Long inTransaction(Handle handle, TransactionStatus status) {
        return handle.createQuery("select count(*) from customer").map(LongMapper.FIRST).first();
      }
    });
  }

  private static List<Customer> createEntities(int n) {
    List<Customer> entities = new ArrayList<>(n);

    for (int i = 1; i <= n; i++) {
      entities.add(new Customer(i));
    }

    return entities;
  }

  protected abstract BulkOperations bulkOperations();

}
