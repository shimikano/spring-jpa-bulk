spring-jpa-bulk
===============

Configuration and simple benchmarks of 5 ways of setting up bulk inserts ("batch" inserts) in a Spring/JPA environment using Hibernate and an in-memory H2 database:

1. Using a default loop of `EntityManager.persist(...)`.

2. Same as 1., but [flushing and clearing][1] the `EntityManager` every `n` iterations.

3. Using Hibernate's [`StatelessSession`][2] and [a community provided Spring integration][3].

4. Using [`JdbcTemplate`][4].

5. Using [JDBI][5].

The project is Maven based.

Benchmarks
----------

Run the benchmarks with
```
mvn test
```

The benchmarks are implemented using [JUnitBenchmarks][6], which conveniently measures the elapsed time of a JUnit test method. The JUnit test method at hand inserts 500000 entities and checks their count.

Sample output snip on my machine (time measurements in seconds):

```
DefaultBulkOperationsTest.bulkInsert:
 round: 6.88 [+- 1.38] ...

FlushingBulkOperationsTest.bulkInsert:
 round: 4.19 [+- 0.75] ...

StatelessSessionBulkOperationsTest.bulkInsert:
 round: 2.57 [+- 0.81] ...

JdbcTemplateBulkOperationsTest.bulkInsert:
 round: 1.79 [+- 0.50] ...

JdbiBulkOperationsTest.bulkInsert:
 round: 4.27 [+- 0.58] ...

```


  [1]: http://docs.jboss.org/hibernate/orm/4.2/manual/en-US/html/ch15.html#batch-inserts
  [2]: http://docs.jboss.org/hibernate/core/4.2/javadocs/org/hibernate/StatelessSession.html
  [3]: https://jira.springsource.org/browse/SPR-2495
  [4]: http://docs.spring.io/spring/docs/3.2.6.RELEASE/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
  [5]: http://jdbi.org/
  [6]: http://labs.carrotsearch.com/junit-benchmarks.html
