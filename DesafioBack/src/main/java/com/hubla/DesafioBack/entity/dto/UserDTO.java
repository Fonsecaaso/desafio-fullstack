package com.hubla.DesafioBack.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {
    @Schema(description = "User name", example = "CELSO DE MELO")
    private String name;
    @Schema(description = "User balance", example = "10000")
    private Double saldo;
    @Schema(description = "If he's or not a producer", example = "true")
    private Boolean produtor;
}
