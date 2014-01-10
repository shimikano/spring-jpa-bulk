package com.acme.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.acme.model.Customer;

public final class FlushingBulkOperations implements BulkOperations {

  private final EntityManager em;

  private final int batchSize;

  public FlushingBulkOperations(EntityManager em, int batchSize) {
    this.em = em;
    this.batchSize = batchSize;
  }

  @Override
  @Transactional
  public void bulkPersist(List<Customer> entities) {
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

  private void flush() {
    em.flush();
  }

  private void clear() {
    em.clear();
  }

}
