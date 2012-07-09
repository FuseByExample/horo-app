package net.jakubkorab.test.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.jakubkorab.test.ResourceHelper;
import org.apache.commons.lang.Validate;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Simple {@link HttpHandler} that
 */
public class ResourceHttpHandler implements HttpHandler {
    private final byte[] response;
    
    public ResourceHttpHandler(Class testClass, String resource) {
        Validate.notNull(testClass, "testClass is null");
        Validate.notEmpty(resource, "resource is empty");
        try {
            response = new ResourceHelper(testClass).getResourceAsString(resource).getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }
}
