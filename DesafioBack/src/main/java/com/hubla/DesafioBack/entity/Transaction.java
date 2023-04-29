package com.hubla.DesafioBack.entity;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "Transaction type", example = "Comissão Paga")
    private String type;
    @Schema(description = "Transaction date", example = "2022-01-15T19:20:30-03:00")
    private String date;
    @Schema(description = "Transaction product", example = "CURSO DE BEM-ESTAR")
    private String product;
    @Schema(description = "Transaction amount in cents", example = "12750")
    private Double value;
    @Schema(description = "Transaction seller", example = "JOSE CARLOS")
    private String sellerName;
}
