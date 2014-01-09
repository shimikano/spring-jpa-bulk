package com.acme.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.acme.model.Customer;

public final class FlushingCustomerRepository extends SimpleJpaRepository<Customer, Long> implements CustomerRepository {

  private final EntityManager em;

  private final int batchSize;

  public FlushingCustomerRepository(EntityManager em, int batchSize) {
    super(Customer.class, em);

    this.em = em;
    this.batchSize = batchSize;
  }

  @Override
  @Transactional
  public void bulkPersist(Iterable<Customer> entities) {
    int i = 0;
    for (Customer entity : entities) {
      em.persist(entity);
      i++;

      if (i % batchSize == 0) {
        flush();
        clear();
      }
    }
  }

  private void clear() {
    em.clear();
  }

}
