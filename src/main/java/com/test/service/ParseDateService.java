package com.test.service;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public interface ParseDateService {
    List<Date> parseDataFromAndTo (@NotNull String date);

    boolean matches(Date date, @NotNull List<Date> dateList);

    Date parseDate (String stringData);
}
