package br.edu.ifpe.recife.planejamento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// O que a empresa quer alcançar. Cada objetivo fica em uma das 4 perspectivas do BSC.
@Entity
public class Objetivo {

    // id é a chave primária: o número que identifica cada linha. Quem gera é o banco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;

    // STRING grava o nome do enum (ex: FINANCEIRA). Sem isso gravaria a posição (0, 1, 2),
    // e o dado mudaria de sentido se alguém mudasse a ordem do enum.
    @Enumerated(EnumType.STRING)
    private Perspectiva perspectiva;

    // Muitos objetivos podem ser da mesma empresa. A coluna empresa_id liga o objetivo à empresa dele.
    // Só o filho aponta pro pai (sem @OneToMany), assim o JSON não entra em loop.
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Perspectiva getPerspectiva() {
        return perspectiva;
    }

    public void setPerspectiva(Perspectiva perspectiva) {
        this.perspectiva = perspectiva;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
