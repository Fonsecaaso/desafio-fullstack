package com.hubla.DesafioBack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venda")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "curso")
    Curso curso;
    Double valor;

    @ManyToOne
    @JoinColumn(name = "vendedor")
    User vendedor;

    public Venda(String produto, Double valor, User vendedor) {
    }

    public User getVendedor() {
        return vendedor;
    }

    public void setVendedor(User vendedor) {
        this.vendedor = vendedor;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public Curso getCurso() {
        return this.curso;
    }
    public Double getValor() {
        return this.valor;
    }
}
