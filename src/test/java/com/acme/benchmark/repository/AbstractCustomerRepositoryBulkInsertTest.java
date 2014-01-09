package com.acme.benchmark.repository;

import java.util.List;

import com.acme.benchmark.AbstractBulkInsertTest;
import com.acme.model.Customer;
import com.acme.repository.CustomerRepository;

/**
 * Base class for implementations operating on an {@link CustomerRepository}.
 */
public abstract class AbstractCustomerRepositoryBulkInsertTest extends AbstractBulkInsertTest {

  @Override
  protected final void bulkInsert(List<Customer> entities) {
    repository().bulkPersist(entities);
  }

  @Override
  protected final long getCount() {
    return repository().count();
  }

  protected abstract CustomerRepository repository();

}
