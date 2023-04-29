package com.hubla.DesafioBack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UploadResponse {
    private List<Error> errors;
    private int successfulCases;
}
