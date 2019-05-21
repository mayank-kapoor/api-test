package com.api.core.utils;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Base {

    protected String covertFileToString(String fileName) {
        String fileContent = null;
        try {
            InputStream fileStream = this.getClass().getResourceAsStream(fileName);
            fileContent = IOUtils.toString(fileStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }
}
