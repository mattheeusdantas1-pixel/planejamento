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
import br.edu.ifpe.recife.planejamento.model.Objetivo;
import br.edu.ifpe.recife.planejamento.repository.EmpresaRepository;
import br.edu.ifpe.recife.planejamento.repository.ObjetivoRepository;

// @RestController: recebe as requisições HTTP e responde em JSON.
// @RequestMapping: todas as rotas daqui começam com /empresas.
@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaRepository repository;
    private final ObjetivoRepository objetivoRepository;

    // O Spring entrega os repositórios pelo construtor.
    public EmpresaController(EmpresaRepository repository, ObjetivoRepository objetivoRepository) {
        this.repository = repository;
        this.objetivoRepository = objetivoRepository;
    }

    @GetMapping
    public List<Empresa> listar() {
        return repository.listar();
    }

    // ResponseEntity deixa escolher o status: 200 se achou, 404 se não existe.
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscar(@PathVariable Long id) {
        Empresa empresa = repository.buscar(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresa);
    }

    // setId(null) garante que é um registro novo, mesmo se o JSON vier com id.
    // Responde 201, que é o status de criado.
    @PostMapping
    public ResponseEntity<Empresa> criar(@RequestBody Empresa empresa) {
        empresa.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.salvar(empresa));
    }

    // O id vem da URL, por isso o setId(id): vale o da rota, não o do JSON.
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        empresa.setId(id);
        return ResponseEntity.ok(repository.salvar(empresa));
    }

    // 204 quer dizer que deu certo e não tem nada para devolver.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        repository.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // Lista os objetivos de uma empresa.
    @GetMapping("/{id}/objetivos")
    public List<Objetivo> listarObjetivos(@PathVariable Long id) {
        return objetivoRepository.listarPorEmpresa(id);
    }
}
