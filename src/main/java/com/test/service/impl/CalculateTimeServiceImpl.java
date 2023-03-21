package com.test.service.impl;

import com.test.model.Query;
import com.test.service.CalculateTimeService;
import com.test.service.ParseDateService;
import com.test.strategy.StrategyStorage;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class CalculateTimeServiceImpl implements CalculateTimeService {
    private static final Integer SERVICE_ID = 1;
    private static final Integer QUESTION_TYPE_ID = 2;
    private static final Integer DATE = 4;
    private static final String DASH = "-";
    private final ParseDateService parseDateService;
    private final StrategyStorage strategyStorage;

    public CalculateTimeServiceImpl(ParseDateService parseDateService, StrategyStorage strategyStorage) {
        this.parseDateService = parseDateService;
        this.strategyStorage = strategyStorage;
    }


    @Override
    public String calculateTime(String [] queries, String strategyKey) {
        List<Query> listQueriesRequestFirst =
                strategyStorage.getStrategy().get(strategyKey).get(getSimpleNumber(queries, SERVICE_ID));

        if (Objects.equals(listQueriesRequestFirst, null)) {
            return DASH;
        }

        AtomicReference<Long> count = new AtomicReference<>(0L);

        if (Objects.equals(queries[QUESTION_TYPE_ID], "*")) {
            long numberRequestAll = listQueriesRequestFirst.stream()
                    .filter(el -> parseDateService.matches(el.getDate(), parseDateService.parseDataFromAndTo(queries[DATE])))
                    .map(el -> count.updateAndGet(v -> v + el.getSeconds()))
                    .count();

            if (numberRequestAll == 0L) {
                return DASH;
            }

            return String.valueOf(Long.parseLong(String.valueOf(count)) / numberRequestAll);
        }

        long numberRequest = listQueriesRequestFirst.stream()
                .filter(el -> Objects.equals(el.getQuestionTypeId(), getSimpleNumber(queries, QUESTION_TYPE_ID)))
                .filter(el -> parseDateService.matches(el.getDate(), parseDateService.parseDataFromAndTo(queries[DATE])))
                .map(el -> count.updateAndGet(v -> v + el.getSeconds()))
                .count();

        if (numberRequest == 0L) {
            return DASH;
        }

        return String.valueOf(Long.parseLong(String.valueOf(count)) / numberRequest);
    }

    private @NotNull Long getSimpleNumber(String @NotNull [] queries, @NotNull Integer partString) {
        return Long.parseLong(queries[partString].split("\\.")[0]);
    }
}
