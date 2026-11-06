package br.edu.ifpe.recife.planejamento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Como a meta é medida: o que medir, em qual unidade e qual valor quer alcançar.
// Também é composição: excluiu a meta, a métrica vai junto.
@Entity
public class Metrica {

    // id é a chave primária: o número que identifica cada linha. Quem gera é o banco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String unidade;

    // O Hibernate (quem faz o JPA funcionar) converte o nome sozinho: valorAlvo no Java vira valor_alvo na tabela.
    private Double valorAlvo;

    // Muitas métricas podem ser da mesma meta. A coluna meta_id liga a métrica à meta dela.
    @ManyToOne
    @JoinColumn(name = "meta_id", nullable = false)
    private Meta meta;

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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getValorAlvo() {
        return valorAlvo;
    }

    public void setValorAlvo(Double valorAlvo) {
        this.valorAlvo = valorAlvo;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
