package ru.sberbank.interview.task.exception;

import java.util.ArrayList;
import java.util.List;

public class MissingIdException extends Exception {

    private final List<Long> missingIds;

    public MissingIdException(String message, List<Long> missingIds) {
        super(message);
        this.missingIds = new ArrayList<>(missingIds);
    }

    public List<Long> getMissingIds() {
        return missingIds;
    }
}
