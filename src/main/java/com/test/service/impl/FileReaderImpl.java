package com.test.service.impl;

import com.test.service.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> readFromFile(Path path) {
        try {
            List<String> strings = Files.readAllLines(path);
            strings.remove(0);
            return strings;
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + path.getFileName());
        }
    }
}
