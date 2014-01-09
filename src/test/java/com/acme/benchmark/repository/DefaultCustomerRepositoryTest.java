package com.acme.benchmark.repository;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.acme.repository.CustomerRepository;

public final class DefaultCustomerRepositoryTest extends AbstractCustomerRepositoryBulkInsertTest {

  @Inject
  @Qualifier("default")
  private CustomerRepository repository;

  @Override
  protected String name() {
    return "Default JpaRepository";
  }

  @Override
  protected CustomerRepository repository() {
    return repository;
  }

}
