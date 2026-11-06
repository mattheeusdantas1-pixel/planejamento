package br.edu.ifpe.recife.planejamento.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.recife.planejamento.model.Metrica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Acesso ao banco para a tabela metrica.
@Repository
public class MetricaRepository {

    // EntityManager é o objeto do JPA que faz as operações no banco.
    @PersistenceContext
    private EntityManager em;

    // JPQL é o SQL do JPA: usa o nome da classe Java, não o nome da tabela.
    public List<Metrica> listar() {
        return em.createQuery("SELECT x FROM Metrica x ORDER BY x.id", Metrica.class).getResultList();
    }

    // Busca pelo id. Se não achar, devolve null.
    public Metrica buscar(Long id) {
        return em.find(Metrica.class, id);
    }

    // @Transactional abre uma transação: ou grava tudo ou não grava nada. Quem grava no banco precisa disso.
    // id null = registro novo, então usa persist (INSERT). Com id, usa merge (UPDATE).
    @Transactional
    public Metrica salvar(Metrica metrica) {
        if (metrica.getId() == null) {
            em.persist(metrica);
            return metrica;
        }
        return em.merge(metrica);
    }

    // Os indicadores dessa métrica são apagados pelo ON DELETE CASCADE do banco.
    @Transactional
    public void excluir(Long id) {
        Metrica metrica = em.find(Metrica.class, id);
        if (metrica != null) {
            em.remove(metrica);
        }
    }

    // Em JPQL a gente usa os atributos do Java: x.meta.id em vez da coluna meta_id.
    public List<Metrica> listarPorMeta(Long metaId) {
        return em.createQuery("SELECT x FROM Metrica x WHERE x.meta.id = :id ORDER BY x.id", Metrica.class)
                .setParameter("id", metaId)
                .getResultList();
    }
}
