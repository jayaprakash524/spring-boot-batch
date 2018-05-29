# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

The goal is to migrate data from one database to another. The way I configured mentioned below
 - Configured Multiple Datasource
 - Configured JdbcTemplate for respective Datasource
 - Configured Step with ItemReader, ItemProcessor, ItemWriter
 - Configured partition step with partition handler
 - Configured Job wth partition step
 - Configured AsyncTaskExecutor to run threads in parallel.