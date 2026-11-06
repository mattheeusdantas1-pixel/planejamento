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

import br.edu.ifpe.recife.planejamento.model.Empresa;
import br.edu.ifpe.recife.planejamento.model.Meta;
import br.edu.ifpe.recife.planejamento.model.Objetivo;
import br.edu.ifpe.recife.planejamento.repository.EmpresaRepository;
import br.edu.ifpe.recife.planejamento.repository.MetaRepository;
import br.edu.ifpe.recife.planejamento.repository.ObjetivoRepository;

// Rotas de objetivo.
@RestController
@RequestMapping("/objetivos")
public class ObjetivoController {

    private final ObjetivoRepository repository;
    private final EmpresaRepository empresaRepository;
    private final MetaRepository metaRepository;

    public ObjetivoController(ObjetivoRepository repository, EmpresaRepository empresaRepository,
                              MetaRepository metaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.metaRepository = metaRepository;
    }

    @GetMapping
    public List<Objetivo> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objetivo> buscar(@PathVariable Long id) {
        Objetivo objetivo = repository.buscar(id);
        if (objetivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(objetivo);
    }

    // setId(null) garante que é um registro novo. Se a empresa não existir, responde 400.
    @PostMapping
    public ResponseEntity<Objetivo> criar(@RequestBody Objetivo objetivo) {
        objetivo.setId(null);
        if (!carregarRelacionamentos(objetivo)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.salvar(objetivo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objetivo> atualizar(@PathVariable Long id, @RequestBody Objetivo objetivo) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        objetivo.setId(id);
        if (!carregarRelacionamentos(objetivo)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.salvar(objetivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        repository.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // Lista as metas de um objetivo.
    @GetMapping("/{id}/metas")
    public List<Meta> listarMetas(@PathVariable Long id) {
        return metaRepository.listarPorObjetivo(id);
    }

    // O JSON traz o pai só com o id. Aqui busca a empresa de verdade e coloca no objetivo.
    // Devolve false se a empresa não veio ou não existe.
    private boolean carregarRelacionamentos(Objetivo objetivo) {
        if (objetivo.getEmpresa() == null || objetivo.getEmpresa().getId() == null) {
            return false;
        }
        Empresa empresa = empresaRepository.buscar(objetivo.getEmpresa().getId());
        if (empresa == null) {
            return false;
        }
        objetivo.setEmpresa(empresa);
        return true;
    }
}
