package br.edu.ifpe.recife.planejamento.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.recife.planejamento.model.Meta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Acesso ao banco para a tabela meta.
@Repository
public class MetaRepository {

    // EntityManager é o objeto do JPA que faz as operações no banco.
    @PersistenceContext
    private EntityManager em;

    // JPQL é o SQL do JPA: usa o nome da classe Java, não o nome da tabela.
    public List<Meta> listar() {
        return em.createQuery("SELECT x FROM Meta x ORDER BY x.id", Meta.class).getResultList();
    }

    // Busca pelo id. Se não achar, devolve null.
    public Meta buscar(Long id) {
        return em.find(Meta.class, id);
    }

    // @Transactional abre uma transação: ou grava tudo ou não grava nada. Quem grava no banco precisa disso.
    // id null = registro novo, então usa persist (INSERT). Com id, usa merge (UPDATE).
    @Transactional
    public Meta salvar(Meta meta) {
        if (meta.getId() == null) {
            em.persist(meta);
            return meta;
        }
        return em.merge(meta);
    }

    // As métricas dessa meta são apagadas pelo ON DELETE CASCADE do banco.
    @Transactional
    public void excluir(Long id) {
        Meta meta = em.find(Meta.class, id);
        if (meta != null) {
            em.remove(meta);
        }
    }

    // Em JPQL a gente usa os atributos do Java: x.objetivo.id em vez da coluna objetivo_id.
    public List<Meta> listarPorObjetivo(Long objetivoId) {
        return em.createQuery("SELECT x FROM Meta x WHERE x.objetivo.id = :id ORDER BY x.id", Meta.class)
                .setParameter("id", objetivoId)
                .getResultList();
    }

    // Conta as metas do departamento. Usado para não deixar excluir departamento que tem meta.
    public boolean existePorDepartamento(Long departamentoId) {
        Long total = em.createQuery("SELECT COUNT(m) FROM Meta m WHERE m.departamento.id = :id", Long.class)
                .setParameter("id", departamentoId)
                .getSingleResult();
        return total > 0;
    }
}
