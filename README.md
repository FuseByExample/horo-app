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