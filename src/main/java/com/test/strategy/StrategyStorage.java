package com.test.strategy;

import com.test.model.Query;
import java.util.HashMap;
import java.util.List;
import static com.test.storage.FirstAnswer.firstAnswerStorage;
import static com.test.storage.SecondAnswer.secondAnswerStorage;

public class StrategyStorage {
    private static StrategyStorage instance;
    private final HashMap<String, HashMap<Long, List<Query>>> strategy;

    private StrategyStorage() {
        strategy = new HashMap<>();
        strategy.put("P", firstAnswerStorage);
        strategy.put("N", secondAnswerStorage);
    }

    public static StrategyStorage getInstance() {
        if (instance == null) {
            instance = new StrategyStorage();
        }
        return instance;
    }

    public HashMap<String, HashMap<Long, List<Query>>> getStrategy() {
        return strategy;
    }
}
