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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
import org.springframework.transaction.support.TransactionTemplate;

import com.acme.model.Customer;
import com.acme.repository.CustomerRepository;
import com.acme.repository.DefaultCustomerRepository;
import com.acme.repository.FlushingCustomerRepository;
import com.acme.repository.StatelessSessionCustomerRepository;

@Configuration
@EnableTransactionManagement
public class TestConfiguration {

  // DataSource

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder.setType(EmbeddedDatabaseType.H2).build();
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
    vendorAdapter.setGenerateDdl(true);

    return vendorAdapter;
  }

  private Map<String, ?> getJpaProperties() {
    Map<String, Object> builder = new HashMap<>();

    // enable JDBC batches in Hibernate
    builder.put(AvailableSettings.STATEMENT_BATCH_SIZE, 50);

    return builder;
  }

  private EntityManagerFactory entityManagerFactory() {
    return entityManagerFactoryBean().getObject();
  }

  // EntityManager

  @Bean
  public SharedEntityManagerBean entityManagerBean() {
    SharedEntityManagerBean bean = new SharedEntityManagerBean();

    bean.setEntityManagerFactory(entityManagerFactory());

    return bean;
  }

  private EntityManager entityManager() {
    return entityManagerBean().getObject();
  }

  // PlatformTransactionManager

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();

    txManager.setEntityManagerFactory(entityManagerFactory());

    return txManager;
  }

  // StatelessSession

  @Bean
  public StatelessSessionFactoryBean statelessSessionFactoryBean() {
    StatelessSessionFactoryBean factory = new StatelessSessionFactoryBean();

    factory.setEntityManagerFactory(entityManagerFactory());

    return factory;
  }

  private StatelessSession statelessSession() {
    return statelessSessionFactoryBean().getObject();
  }

  // repositories

  @Bean
  @Qualifier("default")
  public CustomerRepository defaultCustomerRepository() {
    return new DefaultCustomerRepository(entityManager());
  }

  @Bean
  @Qualifier("flushing")
  public CustomerRepository flushingCustomerRepository() {
    return new FlushingCustomerRepository(entityManager(), batchSize());
  }

  @Bean
  @Qualifier("statelessSession")
  public CustomerRepository statelessSessionCustomerRepository() {
    return new StatelessSessionCustomerRepository(entityManager(), statelessSession());
  }

  // other configuration

  public int batchSize() {
    return 50;
  }

  // JdbcTemplate

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public TransactionTemplate transactionTemplate() {
    // using a separate DataSourceTransactionManager, not the JpaTransactionManager configured above
    return new TransactionTemplate(new DataSourceTransactionManager(dataSource()));
  }

  // JDBI

  @Bean
  public IDBI jdbi() {
    return new DBI(dataSource());
  }

}
