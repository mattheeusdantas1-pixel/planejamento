package br.edu.ifpe.recife.planejamento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Setor da empresa que fica responsável pelas metas (Cozinha, Delivery, etc).
// É uma associação simples: a meta aponta pro departamento, mas não é dona dele.
@Entity
public class Departamento {

    // id é a chave primária: o número que identifica cada linha. Quem gera é o banco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String responsavel;

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

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
