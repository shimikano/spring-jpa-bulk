package com.acme.benchmark.repository;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.acme.repository.CustomerRepository;

public final class FlushingCustomerRepositoryTest extends AbstractCustomerRepositoryBulkInsertTest {

  @Inject
  @Qualifier("flushing")
  private CustomerRepository repository;

  @Override
  protected String name() {
    return "Flushing";
  }

  @Override
  protected CustomerRepository repository() {
    return repository;
  }

}
