package com.test;

import com.test.service.*;
import com.test.service.impl.*;
import com.test.strategy.StrategyStorage;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Main {
    private static final Path PATH_TO_INPUT = Path.of("input.txt");
    private static final Path PATH_TO_OUTPUT = Path.of("output.txt");
    private static final Integer TYPE_QUERIES = 0;
    private static final FileReader fileReader;
    private static final FileWriter fileWriter;
    private static final CalculateTimeService calculateTimeService;
    private static final SaveService saveService;
    private static final ParseDateService parseDateService;
    private static final StrategyStorage strategyStorage = StrategyStorage.getInstance();

    static {
        parseDateService = new ParseDateServiceImpl();
        fileReader = new FileReaderImpl();
        fileWriter = new FileWriterImpl();
        saveService = new SaveServiceImpl(parseDateService, strategyStorage);
        calculateTimeService = new CalculateTimeServiceImpl(parseDateService, strategyStorage);
    }

    public static void main(String[] args) {

        List<String> list = fileReader.readFromFile(PATH_TO_INPUT);

        for (String query: list) {
            String [] queries = query.split(" ");

            if (Objects.equals(queries[TYPE_QUERIES], "C")) {
                saveService.saveToStorage(queries);
                continue;
            }

            PingService pingService = new PingServiceImpl(calculateTimeService);

            String report = pingService.calculatePing(queries);

            fileWriter.writeToFile(PATH_TO_OUTPUT, report);
        }
    }
}
