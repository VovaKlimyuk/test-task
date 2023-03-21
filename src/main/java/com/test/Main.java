package com.test;

import com.test.model.Query;
import com.test.service.FileReader;
import com.test.service.FileReaderImpl;
import com.test.service.FileWriter;
import com.test.service.FileWriterImpl;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static com.test.storage.FirstAnswer.firstAnswerStorage;
import static com.test.storage.SecondAnswer.secondAnswerStorage;

public class Main {
    private static final Integer TYPE_QUERIES = 0;
    private static final Integer SERVICE_ID = 1;
    private static final Integer QUESTION_TYPE_ID = 2;
    private static final Integer ANSWER = 3;
    private static final Integer DATE = 4;
    private static final Integer SECONDS = 5;
    private static final Path PATH_TO_INPUT = Path.of("input.txt");
    private static final Path PATH_TO_OUTPUT = Path.of("output.txt");
    private static final HashMap<String, HashMap<Long, List<Query>>> strategy = new HashMap<>();
    private static final FileWriter fileWriter = new FileWriterImpl();
    private static final FileReader fileReader = new FileReaderImpl();


    public static void main(String[] args) {
        strategy.put("P", firstAnswerStorage);
        strategy.put("N", secondAnswerStorage);

        List<String> list = fileReader.readFromFile(PATH_TO_INPUT);
        list.remove(0);

        for (String query: list) {
            String [] queries = query.split(" ");

            if (Objects.equals(queries[TYPE_QUERIES], "C")) {
                saveToStorage(queries);
                continue;
            }

            if (Objects.equals(queries[ANSWER], "P")) {
                calculateTime(queries, "P");
                continue;
            }

            if (Objects.equals(queries[ANSWER], "N")) {
                calculateTime(queries, "N");
            }
        }
    }

    // save to storage
    private static void saveToStorage(String [] queries) {
        Query queryModel = new Query();
        queryModel.setServiceId(getSimpleNumber(queries, SERVICE_ID));
        queryModel.setQuestionTypeId(getSimpleNumber(queries, QUESTION_TYPE_ID));
        queryModel.setAnswer(queries[ANSWER]);
        queryModel.setDate(parseDate(queries[DATE]));
        queryModel.setSeconds(getSimpleNumber(queries, SECONDS));

        if (Objects.equals(queryModel.getAnswer(), "P")) {
            save("P", queryModel);
        }

        save("N", queryModel);
    }
    private static @NotNull Long getSimpleNumber(String @NotNull [] queries, @NotNull Integer partString) {
        return Long.parseLong(queries[partString].split("\\.")[0]);
    }

    private static void save(String strategyKey, @NotNull Query queryModel) {
        Long key = queryModel.getServiceId();

        if (!Objects.equals(strategy.get(strategyKey).get(key), null)) {
            strategy.get(strategyKey).get(key).add(queryModel);
            return;
        }
        List<Query> listQueries = new ArrayList<>();
        listQueries.add(queryModel);
        strategy.get(strategyKey).put(queryModel.getServiceId(), listQueries);
    }

    // calculate

    private static void calculateTime (String [] queries, String strategyKey) {
        List<Query> listQueriesRequestFirst =
                strategy.get(strategyKey).get(getSimpleNumber(queries, SERVICE_ID));

        if (Objects.equals(listQueriesRequestFirst, null)) {
            saveEmptyReport();
            return;
        }

        AtomicReference<Long> count = new AtomicReference<>(0L);

        if (Objects.equals(queries[QUESTION_TYPE_ID], "*")) {
            long numberRequestAll = listQueriesRequestFirst.stream()
                    .filter(el -> matches(el.getDate(), parseDataFromAndTo(queries[DATE])))
                    .map(el -> count.updateAndGet(v -> v + el.getSeconds())).count();

            if (numberRequestAll == 0L) {
                saveEmptyReport();
                return;
            }

            saveReport(count, numberRequestAll);
            return;
        }

        long numberRequest = listQueriesRequestFirst.stream()
                .filter(el -> Objects.equals(el.getQuestionTypeId(), getSimpleNumber(queries, QUESTION_TYPE_ID)))
                .filter(el -> matches(el.getDate(), parseDataFromAndTo(queries[DATE])))
                .map(el -> count.updateAndGet(v -> v + el.getSeconds())).count();

        if (numberRequest == 0L) {
            saveEmptyReport();
            return;
        }

        saveReport(count, numberRequest);
    }
    private static void saveReport(AtomicReference<Long> count, Long numberRequest) {
        fileWriter.writeToFile(PATH_TO_OUTPUT,
                (Long.parseLong(String.valueOf(count)) / numberRequest) + System.lineSeparator());
    }

    private static void saveEmptyReport() {
        fileWriter.writeToFile(PATH_TO_OUTPUT, "-" + System.lineSeparator());
    }

    private static Date parseDate (String stringData) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            return format.parse(stringData);
        } catch (ParseException e) {
            throw new RuntimeException("Can`t parse data", e);
        }
    }

    private static List<Date> parseDataFromAndTo (@NotNull String date) {
        if (date.length() >= 10) {
            return Arrays.stream(date.split("-"))
                    .map(Main::parseDate)
                    .collect(Collectors.toList());
        }
        return List.of(parseDate(date));
    }

    private static boolean matches(Date date, @NotNull List<Date> dateList) {
        if (dateList.size() > 1) {
            return date.toInstant().isBefore(dateList.get(1).toInstant())
                    && date.toInstant().isAfter(dateList.get(0).toInstant());
        }

        return date.compareTo(dateList.get(0)) == 0;
    }
}
