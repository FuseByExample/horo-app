This project presents a sample app for aggregating and re-presenting horoscopes. It is designed to highlight a number of areas for discussion that are usually overlooked. These include:

* various levels of testing, and their general appropriateness ranging from simple unit tests to integration tests
    * how to get integration tests playing nicely within a shared build environment - loads of developers, a CI server
    * a couple of different ways of testing a Camel route
        * using the standard `CamelSpringTestSupport` approach with `mock:` and `direct:` endpoints
        * exercising an actual component against an embedded web server (assigned to a unique port)
    * writing routes in such a way that they can be easily tested
* setting up MyBatis for data access across OSGi bundles that actively demonstrates the [mybatis](http://www.mybatis.org/core/) and [mybatis-spring](http://www.mybatis.org/spring/index.html) documentation, with
    * Spring transaction management for [camel-mybatis](http://camel.apache.org/mybatis.html)
    * configuration externalised using the OSGi Config Admin mechanisms (property files in the `$SMX_HOME/etc` directory)
    * testing the correctness of data access against an embedded H2 database (contentious and potentially not applicable to all your access needs)
* reuse of expensive resources such as `DataSource`s between disparate bundles through the use of service references
* configuration of database drivers and connection pools ([c3p0](http://www.mchange.com/projects/c3p0/index.html)) in an OSGi environment

Project layout
==============
The Maven projects contained within are as follows:

* `horo-model` - Basic model used by the application
* `horo-db` - Database access; exposes both a DataSource and a Camel Mybatis component for reuse
* `horo-rss-reader` - Initial endpoints for consuming RSS feeds
* `horo-web` - REST endpoints that present a view into the database

All of the above `ws-` bundles are based on Spring DM.

There are three additional parent projects that simplify the Maven project configuration:

* `bundle-parent` - used as parent by all bundles
* `camel-bundle-parent` - used as parent by all bundles
* `horo-features` - Contains an XML features file used to install the rest of the bundles.

Prerequisites
=============

1. This project has been designed against ServiceMix [4.4.1-fuse-08-15](http://repo.fusesource.com/nexus/content/groups/public/org/apache/servicemix/apache-servicemix/4.4.1-fuse-08-15/)
1. The database backend requires an instance of Postgres 9.1, though older versions may work fine - all SQL has been written to the SQL92 standard. [pgAdmin](http://pgadmin.org/) is a great little tool for doing this all visually if you aren't familiar with, or just can't be bothered using, the Postgres command line. To set up the project backend you will need to:
    1. create a database instance called `horo-db` on the local server
    1. connect to the database and run in `horo-db/src/main/resources/sql/schema.sql`
    1. run in `horo-db/src/main/resources/sql/schema.sql`, this will create a login role called `horo-app` with the password `horo-pwd` (you can change this later) and set all the appropriate table permissions



