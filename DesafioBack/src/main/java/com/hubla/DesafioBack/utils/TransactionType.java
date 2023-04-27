package com.hubla.DesafioBack.utils;

public class TransactionType {
    public static String checkType(char ch){
        return switch (ch) {
            case '1' -> "Venda Produtor";
            case '2' -> "Venda Afiliado";
            case '3' -> "Comissão Paga";
            case '4' -> "Comissão Recebida";
            default -> "erro";
        };
    }
}