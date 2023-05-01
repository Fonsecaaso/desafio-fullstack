package com.hubla.DesafioBack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product")
    Product product;
    Double value;

    @ManyToOne
    @JoinColumn(name = "seller")
    UserEntity seller;
}
