package br.edu.ifpe.recife.planejamento.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// O que precisa ser feito, e até quando, para chegar no objetivo.
// No diagrama a meta é parte do objetivo (composição): excluiu o objetivo, a meta vai junto.
// Quem apaga junto é o ON DELETE CASCADE do schema.sql.
@Entity
public class Meta {

    // id é a chave primária: o número que identifica cada linha. Quem gera é o banco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private LocalDate prazo;

    // Muitas metas podem ser do mesmo objetivo. A coluna objetivo_id liga a meta ao objetivo dela.
    @ManyToOne
    @JoinColumn(name = "objetivo_id", nullable = false)
    private Objetivo objetivo;

    // Departamento responsável pela meta. É opcional, por isso não tem nullable = false.
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
