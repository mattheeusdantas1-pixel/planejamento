package br.edu.ifpe.recife.planejamento.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.recife.planejamento.model.Indicador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Acesso ao banco para a tabela indicador.
@Repository
public class IndicadorRepository {

    // EntityManager é o objeto do JPA que faz as operações no banco.
    @PersistenceContext
    private EntityManager em;

    // JPQL é o SQL do JPA: usa o nome da classe Java, não o nome da tabela.
    public List<Indicador> listar() {
        return em.createQuery("SELECT x FROM Indicador x ORDER BY x.id", Indicador.class).getResultList();
    }

    // Busca pelo id. Se não achar, devolve null.
    public Indicador buscar(Long id) {
        return em.find(Indicador.class, id);
    }

    // @Transactional abre uma transação: ou grava tudo ou não grava nada. Quem grava no banco precisa disso.
    // id null = registro novo, então usa persist (INSERT). Com id, usa merge (UPDATE).
    @Transactional
    public Indicador salvar(Indicador indicador) {
        if (indicador.getId() == null) {
            em.persist(indicador);
            return indicador;
        }
        return em.merge(indicador);
    }

    // Busca antes de remover porque o remove só aceita objeto que veio do banco.
    @Transactional
    public void excluir(Long id) {
        Indicador indicador = em.find(Indicador.class, id);
        if (indicador != null) {
            em.remove(indicador);
        }
    }

    // Em JPQL a gente usa os atributos do Java: x.metrica.id em vez da coluna metrica_id.
    public List<Indicador> listarPorMetrica(Long metricaId) {
        return em.createQuery("SELECT x FROM Indicador x WHERE x.metrica.id = :id ORDER BY x.id", Indicador.class)
                .setParameter("id", metricaId)
                .getResultList();
    }
}
