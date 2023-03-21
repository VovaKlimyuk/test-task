package com.test.service.impl;

import com.test.service.CalculateTimeService;
import com.test.service.PingService;
import java.util.Objects;

public class PingServiceImpl implements PingService {
    private final Integer ANSWER = 3;
    private final CalculateTimeService calculateTimeService;

    public PingServiceImpl(CalculateTimeService calculateTimeService) {
        this.calculateTimeService = calculateTimeService;
    }

    @Override
    public String calculatePing(String[] queries) {

        if (Objects.equals(queries[ANSWER], "P")) {
            return calculateTimeService.calculateTime(queries, "P")
                    + System.lineSeparator();
        }

        return calculateTimeService.calculateTime(queries, "N")
                + System.lineSeparator();
    }
}
