package br.edu.ifpe.recife.planejamento.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Valor medido de verdade em uma data. Comparando com o valorAlvo da métrica, dá pra ver
// se a meta está perto ou longe. Uma métrica tem vários indicadores, um por medição.
@Entity
public class Indicador {

    // id é a chave primária: o número que identifica cada linha. Quem gera é o banco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double valor;

    // dataMedicao no Java vira data_medicao na tabela.
    private LocalDate dataMedicao;
    private String observacao;

    // Muitos indicadores podem ser da mesma métrica. A coluna metrica_id liga os dois.
    @ManyToOne
    @JoinColumn(name = "metrica_id", nullable = false)
    private Metrica metrica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataMedicao() {
        return dataMedicao;
    }

    public void setDataMedicao(LocalDate dataMedicao) {
        this.dataMedicao = dataMedicao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Metrica getMetrica() {
        return metrica;
    }

    public void setMetrica(Metrica metrica) {
        this.metrica = metrica;
    }
}
