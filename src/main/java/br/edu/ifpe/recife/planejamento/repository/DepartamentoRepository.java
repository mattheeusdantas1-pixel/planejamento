package br.edu.ifpe.recife.planejamento.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.recife.planejamento.model.Departamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Acesso ao banco para a tabela departamento.
@Repository
public class DepartamentoRepository {

    // EntityManager é o objeto do JPA que faz as operações no banco.
    @PersistenceContext
    private EntityManager em;

    // JPQL é o SQL do JPA: usa o nome da classe Java, não o nome da tabela.
    public List<Departamento> listar() {
        return em.createQuery("SELECT x FROM Departamento x ORDER BY x.id", Departamento.class).getResultList();
    }

    // Busca pelo id. Se não achar, devolve null.
    public Departamento buscar(Long id) {
        return em.find(Departamento.class, id);
    }

    // @Transactional abre uma transação: ou grava tudo ou não grava nada. Quem grava no banco precisa disso.
    // id null = registro novo, então usa persist (INSERT). Com id, usa merge (UPDATE).
    @Transactional
    public Departamento salvar(Departamento departamento) {
        if (departamento.getId() == null) {
            em.persist(departamento);
            return departamento;
        }
        return em.merge(departamento);
    }

    // Busca antes de remover porque o remove só aceita objeto que veio do banco.
    @Transactional
    public void excluir(Long id) {
        Departamento departamento = em.find(Departamento.class, id);
        if (departamento != null) {
            em.remove(departamento);
        }
    }
}
