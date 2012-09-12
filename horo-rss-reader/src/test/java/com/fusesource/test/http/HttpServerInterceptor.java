/*
 * Copyright 2012 FuseSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fusesource.test.http;

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
    private final Class testClass;
    private final Map<String, HttpHandler> pathHandlers = new HashMap<String, HttpHandler>();

    public HttpServerInterceptor(Class testClass) {
        Validate.notNull(testClass, "testClass is null");
        this.testClass = testClass;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(0), 0); // automatically assigns a free port
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        return httpServer.getAddress().getPort();
    }
}