package com.example.light_up_travel.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmptyFileException extends  RuntimeException{
    private final String msg;
}
