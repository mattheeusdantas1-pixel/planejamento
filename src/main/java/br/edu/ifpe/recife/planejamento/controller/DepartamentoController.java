package br.edu.ifpe.recife.planejamento.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpe.recife.planejamento.model.Departamento;
import br.edu.ifpe.recife.planejamento.repository.DepartamentoRepository;
import br.edu.ifpe.recife.planejamento.repository.MetaRepository;

// Rotas de departamento.
@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoRepository repository;
    private final MetaRepository metaRepository;

    public DepartamentoController(DepartamentoRepository repository, MetaRepository metaRepository) {
        this.repository = repository;
        this.metaRepository = metaRepository;
    }

    @GetMapping
    public List<Departamento> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> buscar(@PathVariable Long id) {
        Departamento departamento = repository.buscar(id);
        if (departamento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departamento);
    }

    // setId(null) garante que é um registro novo.
    @PostMapping
    public ResponseEntity<Departamento> criar(@RequestBody Departamento departamento) {
        departamento.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.salvar(departamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> atualizar(@PathVariable Long id, @RequestBody Departamento departamento) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        departamento.setId(id);
        return ResponseEntity.ok(repository.salvar(departamento));
    }

    // Departamento não é dono da meta, então não tem cascata. Se tiver meta ligada,
    // o banco recusaria a exclusão. Por isso testa antes e responde 409 com uma mensagem.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        if (metaRepository.existePorDepartamento(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Esse departamento é responsável por metas e não pode ser excluído.");
        }
        repository.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
