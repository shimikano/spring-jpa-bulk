package com.acme.benchmark.repository;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.acme.repository.CustomerRepository;

public final class StatelessSessionCustomerRepositoryTest extends AbstractCustomerRepositoryBulkInsertTest {

  @Inject
  @Qualifier("statelessSession")
  private CustomerRepository repository;

  @Override
  protected String name() {
    return "StatelessSession";
  }

  @Override
  protected CustomerRepository repository() {
    return repository;
  }

}
