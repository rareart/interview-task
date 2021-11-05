package ru.sberbank.interview.task.controller.dto.support;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
}
