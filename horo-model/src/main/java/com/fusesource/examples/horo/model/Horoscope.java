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

package com.fusesource.examples.horo.model;

import org.joda.time.DateTime;

public class Horoscope {
    private Long id;
    private DateTime predictsFor;
    private StarSign starSign;
    private String entry;
    private Feed feed;

    @Override
    public String toString() {
        return "Horoscope{" +
                "id=" + id +
                ", predictsFor=" + predictsFor +
                ", starSign=" + starSign +
                ", entry='" + entry + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getPredictsFor() {
        return predictsFor;
    }

    public void setPredictsFor(DateTime predictsFor) {
        this.predictsFor = predictsFor;
    }

    public StarSign getStarSign() {
        return starSign;
    }

    public void setStarSign(StarSign starSign) {
        this.starSign = starSign;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }

}