package com.test.service;

import java.nio.file.Path;

public interface FileWriter {
    void writeToFile(Path path, String report);
}
