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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fusesource.test.ResourceHelper;
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
