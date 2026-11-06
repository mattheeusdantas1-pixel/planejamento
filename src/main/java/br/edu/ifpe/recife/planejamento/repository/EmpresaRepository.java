package br.edu.ifpe.recife.planejamento.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.recife.planejamento.model.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// @Repository: classe que fala com o banco. O Spring cria ela e entrega pro controller.
@Repository
public class EmpresaRepository {

    // EntityManager é o objeto do JPA que faz as operações no banco. O Spring coloca ele aqui.
    @PersistenceContext
    private EntityManager em;

    // Essa consulta é JPQL, o SQL do JPA: usa o nome da classe (Empresa), não o nome da tabela.
    public List<Empresa> listar() {
        return em.createQuery("SELECT x FROM Empresa x ORDER BY x.id", Empresa.class).getResultList();
    }

    // find busca pelo id. Se não achar, devolve null.
    public Empresa buscar(Long id) {
        return em.find(Empresa.class, id);
    }

    // @Transactional abre uma transação: ou grava tudo ou não grava nada. Quem grava no banco precisa disso.
    // id null = registro novo, então usa persist (INSERT). Com id, usa merge (UPDATE).
    @Transactional
    public Empresa salvar(Empresa empresa) {
        if (empresa.getId() == null) {
            em.persist(empresa);
            return empresa;
        }
        return em.merge(empresa);
    }

    // Busca antes de remover porque o remove só aceita objeto que veio do banco.
    @Transactional
    public void excluir(Long id) {
        Empresa empresa = em.find(Empresa.class, id);
        if (empresa != null) {
            em.remove(empresa);
        }
    }
}
