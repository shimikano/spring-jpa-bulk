package com.acme.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.StatelessSession;
import org.hibernate.cfg.AvailableSettings;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.IDBI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.acme.model.Customer;
import com.acme.repository.custom.BulkOperations;
import com.acme.repository.custom.DefaultBulkOperations;
import com.acme.repository.custom.FlushingBulkOperations;
import com.acme.repository.custom.JdbcTemplateBulkOperations;
import com.acme.repository.custom.JdbiBulkOperations;
import com.acme.repository.custom.StatelessSessionBulkOperations;

@Configuration
@EnableTransactionManagement
public class TestConfiguration {

  // DataSource

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

    builder.setType(EmbeddedDatabaseType.H2);
    builder.addScript("classpath:/schema.sql");

    return builder.build();
  }

  // EntityManagerFactory

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

    factory.setDataSource(dataSource());
    factory.setPackagesToScan(Customer.class.getPackage().getName());

    factory.setJpaVendorAdapter(vendorAdapter());
    factory.setJpaPropertyMap(getJpaProperties());

    return factory;
  }

  private JpaVendorAdapter vendorAdapter() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

    vendorAdapter.setDatabase(Database.H2);

    return vendorAdapter;
  }

  private Map<String, ?> getJpaProperties() {
    Map<String, Object> builder = new HashMap<>();

    // enable JDBC batches in Hibernate
    builder.put(AvailableSettings.STATEMENT_BATCH_SIZE, batchSize());

    return builder;
  }

  // EntityManager

  @Bean
  public SharedEntityManagerBean entityManagerBean(EntityManagerFactory entityManagerFactory) {
    SharedEntityManagerBean bean = new SharedEntityManagerBean();

    bean.setEntityManagerFactory(entityManagerFactory);

    return bean;
  }

  // TransactionManager

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  // StatelessSession

  @Bean
  public StatelessSessionFactoryBean statelessSessionFactoryBean(EntityManagerFactory entityManagerFactory) {
    StatelessSessionFactoryBean factory = new StatelessSessionFactoryBean();

    factory.setEntityManagerFactory(entityManagerFactory);

    return factory;
  }

  // repositories

  @Bean
  @Qualifier("default")
  public BulkOperations defaultBulkOperations(EntityManager entityManager) {
    return new DefaultBulkOperations(entityManager);
  }

  @Bean
  @Qualifier("flushing")
  public BulkOperations flushingBulkOperations(EntityManager entityManager) {
    return new FlushingBulkOperations(entityManager, batchSize());
  }

  @Bean
  @Qualifier("statelessSession")
  public BulkOperations statelessSessionCustomerRepository(EntityManager entityManager,
      StatelessSession statelessSession) {
    return new StatelessSessionBulkOperations(entityManager, statelessSession);
  }

  @Bean
  @Qualifier("jdbcTemplate")
  public BulkOperations jdbcTemplateBulkOperations() {
    return new JdbcTemplateBulkOperations(jdbcTemplate());
  }

  @Bean
  @Qualifier("jdbi")
  public BulkOperations jdbiBulkOperations() {
    return new JdbiBulkOperations(jdbi(), batchSize());
  }

  // JdbcTemplate

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  // JDBI

  @Bean
  public IDBI jdbi() {
    return new DBI(dataSource());
  }

  // other configuration

  public int batchSize() {
    return 50;
  }

}
