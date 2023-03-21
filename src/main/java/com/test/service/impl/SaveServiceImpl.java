package com.test.service.impl;

import com.test.model.Query;
import com.test.service.ParseDateService;
import com.test.service.SaveService;
import com.test.strategy.StrategyStorage;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaveServiceImpl implements SaveService {
    private static final Integer SERVICE_ID = 1;
    private static final Integer QUESTION_TYPE_ID = 2;
    private static final Integer ANSWER = 3;
    private static final Integer DATE = 4;
    private static final Integer SECONDS = 5;

    private final ParseDateService parseDateService;
    private final StrategyStorage strategyStorage;

    public SaveServiceImpl(ParseDateService parseDateService, StrategyStorage strategyStorage) {
        this.parseDateService = parseDateService;
        this.strategyStorage = strategyStorage;
    }

    @Override
    public void saveToStorage (String[] queries) {
        Query queryModel = new Query();
        queryModel.setServiceId(getSimpleNumber(queries, SERVICE_ID));
        queryModel.setQuestionTypeId(getSimpleNumber(queries, QUESTION_TYPE_ID));
        queryModel.setAnswer(queries[ANSWER]);
        queryModel.setDate(parseDateService.parseDate(queries[DATE]));
        queryModel.setSeconds(getSimpleNumber(queries, SECONDS));

        if (Objects.equals(queryModel.getAnswer(), "P")) {
            save("P", queryModel);
        }

        save("N", queryModel);
    }

    private void save(String strategyKey, @NotNull Query queryModel) {
        Long key = queryModel.getServiceId();

        if (!Objects.equals(strategyStorage.getStrategy().get(strategyKey).get(key), null)) {
            strategyStorage.getStrategy().get(strategyKey).get(key).add(queryModel);
            return;
        }
        List<Query> listQueries = new ArrayList<>();
        listQueries.add(queryModel);
        strategyStorage.getStrategy().get(strategyKey).put(queryModel.getServiceId(), listQueries);
    }

    private @NotNull Long getSimpleNumber(String @NotNull [] queries, @NotNull Integer partString) {
        return Long.parseLong(queries[partString].split("\\.")[0]);
    }
}
