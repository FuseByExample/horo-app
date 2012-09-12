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

package com.fusesource.examples.horo.db;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This test class requires a visual test. Should see log statement:
 * SpringManagedTransaction       DEBUG JDBC Connection [....] will be managed by Spring
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-context-props.xml",
        "/test-context-h2.xml",
        "/META-INF/spring/spring-context-mybatis.xml",
        "InsertRouteTransactedITCase-context.xml"})
public class InsertRouteTransactedITCase extends InsertRouteITBase {
}
