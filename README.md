spring-jpa-bulk
===============

Configuration and simple benchmarks of 3 ways of setting up bulk inserts ("batch" inserts) in a Spring/JPA environment using Hibernate and an in-memory H2 database:

1. Using a default [`JpaRepository`][1].

2. Using a customized `JpaRepository` that [flushes and clears][2] the `EntityManager` every `n` iterations.

3. Using a customized `JpaRepository` that utilizes Hibernate's [`StatelessSession`][3] and [a community provided Spring integration][4].

For comparison, a bare [`JdbcTemplate`][5] and [JDBI][6] way are also included.

The project is Maven based.

Benchmarks
----------

Run the benchmarks using `mvn test`.

The benchmarks simply measure the elapsed time (using slf4j-ext's [`Profiler`][7]) needed to insert 100000 entities.

Sample output snip on my machine:

```
+ Profiler [Bulk insert - Default JpaRepository]
|-- elapsed time        [100000 entities]  3889.483 milliseconds.
|-- Total        [Bulk insert - Default JpaRepository]  3889.487 milliseconds.

+ Profiler [Bulk insert - Flushing]
|-- elapsed time        [100000 entities]  3647.939 milliseconds.
|-- Total        [Bulk insert - Flushing]  3648.340 milliseconds.

+ Profiler [Bulk insert - StatelessSession]
|-- elapsed time        [100000 entities]   363.287 milliseconds.
|-- Total        [Bulk insert - StatelessSession]   363.290 milliseconds.

+ Profiler [Bulk insert - JDBI]
|-- elapsed time        [100000 entities]   814.524 milliseconds.
|-- Total            [Bulk insert - JDBI]   814.528 milliseconds.

+ Profiler [Bulk insert - JdbcTemplate]
|-- elapsed time        [100000 entities]    99.296 milliseconds.
|-- Total        [Bulk insert - JdbcTemplate]    99.299 milliseconds.

```


  [1]: http://docs.spring.io/spring-data/jpa/docs/1.4.x/api/org/springframework/data/jpa/repository/JpaRepository.html
  [2]: http://docs.jboss.org/hibernate/orm/4.2/manual/en-US/html/ch15.html#batch-inserts
  [3]: http://docs.jboss.org/hibernate/core/4.2/javadocs/org/hibernate/StatelessSession.html
  [4]: https://jira.springsource.org/browse/SPR-2495
  [5]: http://docs.spring.io/spring/docs/3.2.6.RELEASE/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
  [6]: http://jdbi.org/
  [7]: http://www.slf4j.org/extensions.html#profiler
