package br.edu.ifpe.recife.planejamento.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.recife.planejamento.model.Objetivo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Acesso ao banco para a tabela objetivo.
@Repository
public class ObjetivoRepository {

    // EntityManager é o objeto do JPA que faz as operações no banco.
    @PersistenceContext
    private EntityManager em;

    // JPQL é o SQL do JPA: usa o nome da classe Java, não o nome da tabela.
    public List<Objetivo> listar() {
        return em.createQuery("SELECT x FROM Objetivo x ORDER BY x.id", Objetivo.class).getResultList();
    }

    // Busca pelo id. Se não achar, devolve null.
    public Objetivo buscar(Long id) {
        return em.find(Objetivo.class, id);
    }

    // @Transactional abre uma transação: ou grava tudo ou não grava nada. Quem grava no banco precisa disso.
    // id null = registro novo, então usa persist (INSERT). Com id, usa merge (UPDATE).
    @Transactional
    public Objetivo salvar(Objetivo objetivo) {
        if (objetivo.getId() == null) {
            em.persist(objetivo);
            return objetivo;
        }
        return em.merge(objetivo);
    }

    // As metas desse objetivo são apagadas pelo ON DELETE CASCADE do banco.
    @Transactional
    public void excluir(Long id) {
        Objetivo objetivo = em.find(Objetivo.class, id);
        if (objetivo != null) {
            em.remove(objetivo);
        }
    }

    // Em JPQL a gente usa os atributos do Java: x.empresa.id em vez da coluna empresa_id.
    // :id é um parâmetro, preenchido pelo setParameter.
    public List<Objetivo> listarPorEmpresa(Long empresaId) {
        return em.createQuery("SELECT x FROM Objetivo x WHERE x.empresa.id = :id ORDER BY x.id", Objetivo.class)
                .setParameter("id", empresaId)
                .getResultList();
    }
}
