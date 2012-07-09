package net.jakubkorab.test;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.Validate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceHelper {
    private Class testClass;

    public ResourceHelper(Class testClass) {
        Validate.notNull(testClass, "testClass is null");
        this.testClass = testClass;
    }

    public String getResourceAsString(String fileName) throws IOException {
        Validate.notEmpty(fileName, "fileName is empty");
        InputStream inputStream = testClass.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new RuntimeException("Could not find " + fileName);
        }
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            } else {
                out.append(line + SystemUtils.LINE_SEPARATOR);
            }
        }
        return out.toString();
    }
}