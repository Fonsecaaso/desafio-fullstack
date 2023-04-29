package com.hubla.DesafioBack.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String line;
    private String message;
    private String lineContent;
}
