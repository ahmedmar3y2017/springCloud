package com.auth.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class ValidationErrorResponse {
    private int status;
    private String error;
    private List<String> messages;
}

