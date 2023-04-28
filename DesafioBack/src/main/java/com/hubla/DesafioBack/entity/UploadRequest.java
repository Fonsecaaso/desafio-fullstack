package com.hubla.DesafioBack.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UploadRequest {
    @Schema(description = "txt file containing transactions", example = "12022-01-15T19:20:30-03:00CURSO DE BEM-ESTAR            0000012750JOSE CARLOS\n" +
            "12021-12-03T11:46:02-03:00DOMINANDO INVESTIMENTOS       0000050000MARIA CANDIDA")
    String file;
}
