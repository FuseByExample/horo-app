package net.jakubkorab.test.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang.Validate;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Note: this class only works on JDKs that contain {@link com.sun.net.httpserver.HttpServer}
 */
public class HttpServerInterceptor extends ExternalResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final HttpServer httpServer;
    private final int port;
    private final Class testClass;
    private final Map<String, HttpHandler> pathHandlers = new HashMap<String, HttpHandler>();

    public HttpServerInterceptor(Class testClass, int port) {
        Validate.notNull(testClass, "testClass is null");
        //Validate.isTrue(port > 0, "port must be greater than 0");
        this.testClass = testClass;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.port = httpServer.getAddress().getPort();
    }

    @Override
    public void before() throws IOException {
        log.info("Starting HttpServer");
        for (Map.Entry<String, HttpHandler> entry : pathHandlers.entrySet()) {
            httpServer.createContext(entry.getKey(), entry.getValue());
        }
        httpServer.start();
    }

    @Override
    public void after() {
        log.info("Stopping HttpServer");
        httpServer.stop(0);
    }

    public HttpServerInterceptor respondsTo(String contextPath, String handlerResource) {
        Validate.notEmpty(contextPath, "contextPath is empty");
        Validate.notNull(handlerResource, "handlerResource is null");
        pathHandlers.put(contextPath, new ResourceHttpHandler(testClass, handlerResource));
        return this;
    }

    public int getPort() {
        return port;
    }
}