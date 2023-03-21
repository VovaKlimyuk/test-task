package com.test.service.impl;

import com.test.service.ParseDateService;
import org.jetbrains.annotations.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ParseDateServiceImpl implements ParseDateService {
    @Override
    public List<Date> parseDataFromAndTo(@NotNull String date) {
        if (date.length() >= 10) {
            return Arrays.stream(date.split("-"))
                    .map(this::parseDate)
                    .collect(Collectors.toList());
        }
        return List.of(parseDate(date));
    }

    @Override
    public boolean matches(Date date, @NotNull List<Date> dateList) {
        if (dateList.size() > 1) {
            return date.toInstant().isBefore(dateList.get(1).toInstant())
                    && date.toInstant().isAfter(dateList.get(0).toInstant());
        }

        return date.compareTo(dateList.get(0)) == 0;
    }

    @Override
    public Date parseDate(String stringData) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            return format.parse(stringData);
        } catch (ParseException e) {
            throw new RuntimeException("Can`t parse data", e);
        }
    }
}
