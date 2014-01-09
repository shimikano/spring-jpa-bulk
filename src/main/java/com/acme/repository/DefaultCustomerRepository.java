package com.acme.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.acme.model.Customer;

public final class DefaultCustomerRepository extends SimpleJpaRepository<Customer, Long> implements CustomerRepository {

  public DefaultCustomerRepository(EntityManager em) {
    super(Customer.class, em);
  }

  @Override
  @Transactional
  public void bulkPersist(Iterable<Customer> entities) {
    save(entities); // just delegating to the default
  }

}
