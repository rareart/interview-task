package ru.sberbank.interview.task.controller.dto.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
}
