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

package com.fusesource.examples.horo.rssReader;

import com.fusesource.examples.horo.model.StarSign;
import org.apache.camel.Header;
import org.apache.commons.lang.Validate;

import java.util.StringTokenizer;

public class StarSignParser {

    public StarSign parse(@Header("title") String title) {
        Validate.notEmpty(title, "title is empty");

        StarSign starSign = null;
        StringTokenizer tokenizer = new StringTokenizer(title, " ");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            starSign = StarSign.getInstance(token);
            if (starSign != null) {
                break;
            }
        }
        return starSign;
    }
}
