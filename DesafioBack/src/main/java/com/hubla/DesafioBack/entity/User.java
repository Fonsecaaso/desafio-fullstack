package com.hubla.DesafioBack.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;

    private Double saldo;

    private Boolean produtor;


    public User(String nome, Double saldo, boolean produtor) {
        this.nome = nome;
        this.saldo = saldo;
        this.produtor = produtor;
    }


    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSaldo() {
        return saldo;
    }
    public Boolean getProdutor() {
        return produtor;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}

