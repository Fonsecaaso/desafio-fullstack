package com.hubla.DesafioBack.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Erro {
    private String linha;
    private String message;
    private String conteudoInput;
}
