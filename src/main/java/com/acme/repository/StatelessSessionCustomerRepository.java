package com.acme.repository;

import javax.persistence.EntityManager;

import org.hibernate.StatelessSession;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.acme.model.Customer;

public final class StatelessSessionCustomerRepository extends SimpleJpaRepository<Customer, Long> implements
    CustomerRepository {

  private final StatelessSession statelessSession;

  public StatelessSessionCustomerRepository(EntityManager em, StatelessSession statelessSession) {
    super(Customer.class, em);
    this.statelessSession = statelessSession;
  }

  @Override
  @Transactional
  public void bulkPersist(Iterable<Customer> entities) {
    for (Customer entity : entities) {
      statelessSession.insert(entity);
    }
  }

}
