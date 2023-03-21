package com.test.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(Path path, String report) {
        try {
            Files.writeString(path, report, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file -> " + path.getFileName());
        }
    }
}
