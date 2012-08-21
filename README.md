This is an app for aggregating and re-presenting horoscopes.


Project layout
==============
The Maven projects contained within are as follows:

* `horo-model` - Basic model used by the application
* `horo-db` - Database access; exposes both a DataSource and a Camel Mybatis component for reuse
* `horo-rss-reader` - Initial endpoints for consuming RSS feeds
* `horo-web` - REST endpoints that present a view into the database

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
* TODO add jasypt encryption to db credentials
* TODO migrations - http://www.mybatis.org/migrations/
* TODO write out starsign scrubbing code.
* TODO remove need for enforcer to run every time

Scrapers
* TODO horoscopes.co.uk - scraping
* TODO horoscope.com - scraping, 1st <div id="textline">
** http://www.mirror.co.uk/lifestyle/horoscopes/daily/aries/
** div[class="horoscopes-intro"]/h2/Today's Horoscope/p/Make a special point of occupying yourself with tasks you find easy to do. Focus on jobs that will not need a lot of concentration or specialist knowledge. Complicated ventures will only have you running around in circles. These are best left alone until you feel more able to handle the challenge.

* TODO Error handling for odd scrapers.