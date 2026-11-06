package br.edu.ifpe.recife.planejamento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity diz pro JPA que essa classe representa uma tabela, aqui a tabela empresa.
// A empresa guarda a missão e a visão, e é o topo do BSC: os objetivos pertencem a ela.
@Entity
public class Empresa {

    // id é a chave primária: o número que identifica cada linha. IDENTITY = quem gera é o banco (AUTO_INCREMENT).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String missao;
    private String visao;

    // Getters e setters: o JSON usa eles para ler e preencher os campos.
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

    public String getMissao() {
        return missao;
    }

    public void setMissao(String missao) {
        this.missao = missao;
    }

    public String getVisao() {
        return visao;
    }

    public void setVisao(String visao) {
        this.visao = visao;
    }
}
