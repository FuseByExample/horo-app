This is an app for checking the validity of horoscopes


Project layout
==============
The Maven projects contained within are as follows:

* `greeter-api` - Contains a WSDL file from which Java objects are generated at build time. 
* `ws-cxf-service` - Contains a bundle with a concrete "naked" CXF service implementation; no Camel is used here.
* `ws-camel-service` - Contains a bundle with a Camel CXF endpoint that picks up all requests from a web service and dynamically responds. No concrete implementation ins necessary here.
* `ws-camel-proxy` - Contains a bundle with a Camel route that proxies requests from the bundle's CXF endpoint to a remote instance of the service.
* `ws-camel-client` - Contains a Camel route that sends request on a round-robin basis between the services from the bundles above.

All of the above `ws-` bundles are based on Spring DM.

There are two additional parent projects that simplify the Maven project configuration:

* `bundle-parent` - used as parent by all bundles
* `camel-bundle-parent` - used as parent by all bundles
* `horo-features` - Contains an XML features file used to install the rest of the bundles.

Prerequisites
=============
An open mind.

TODO
====
Table insert
Testing against an embedded database
Date parsing
Migrations - http://www.mybatis.org/migrations/

horoscopes.co.uk - scraping
horoscope.com - scraping, 1st <div id="textline">

http://www.mirror.co.uk/lifestyle/horoscopes/daily/aries/
div[class="horoscopes-intro"]/h2/Today's Horoscope/p/Make a special point of occupying yourself with tasks you find easy to do. Focus on jobs that will not need a lot of concentration or specialist knowledge. Complicated ventures will only have you running around in circles. These are best left alone until you feel more able to handle the challenge.

Move resources/astrology.com 
	-> resources/com/astrology
	-> resources/com/astrology/extended

Write out starsign scrubbing login.
Error handling for odd scrapers.