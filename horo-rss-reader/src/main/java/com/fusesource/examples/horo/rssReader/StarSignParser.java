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
