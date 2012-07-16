This is an app for checking the validity of horoscopes


Project layout
==============
The Maven projects contained within are as follows:

* `horo-model` - Basic model used by the application
* `horo-db` - Database access; exposes both a DataSource and a Camel Mybatis component for reuse
* `horo-rss-reader` - Initial endpoints for consuming RSS feeds
** TODO split into a template bundle and service instance bundles
* TODO `horo-web` - REST endpoints that present a vie into the database

All of the above `ws-` bundles are based on Spring DM.

There are two additional parent projects that simplify the Maven project configuration:

* `bundle-parent` - used as parent by all bundles
* `camel-bundle-parent` - used as parent by all bundles
* `horo-features` - Contains an XML features file used to install the rest of the bundles.

Prerequisites
=============
An open mind.

Outstanding work
Basics
* FIXME table insert not happening due to Postgres datasource commit
** org.postgresql.util.PSQLException: Cannot commit when autoCommit is enabled.
*** c3p0 sets autoCommitOnClose to false, or so it says
*** happens regardless of whether there is a transaction manager defined or not
** MyBatisProducer does a session.commit() followed by session.close()
*** stubbed out session.commit and it's not committing at all - autoCommitOnClose=true?
** SessionFactoryBean ->  c3p0 PooledDataSource -> PGSimpleDataSource
** all interactions are with the SessionFactory, no mention of transaction managers
*** see SessionFactoryBean for transaction setup
* TODO add jasypt encryption to db credentials
* TODO remove need for enforcer to run every time
* TODO avoid duplicate inserts http://camel.apache.org/idempotent-consumer.html
* TODO dependency on debuggable Camel version? internally the camel bundles depend on minor versioned components only
* TODO migrations - http://www.mybatis.org/migrations/
* TODO write out starsign scrubbing code.
* TODO restful web service endpoints

Scrapers
* TODO horoscopes.co.uk - scraping
* TODO horoscope.com - scraping, 1st <div id="textline">
** http://www.mirror.co.uk/lifestyle/horoscopes/daily/aries/
** div[class="horoscopes-intro"]/h2/Today's Horoscope/p/Make a special point of occupying yourself with tasks you find easy to do. Focus on jobs that will not need a lot of concentration or specialist knowledge. Complicated ventures will only have you running around in circles. These are best left alone until you feel more able to handle the challenge.

* TODO Error handling for odd scrapers.