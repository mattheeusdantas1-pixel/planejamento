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
import br.edu.ifpe.recife.planejamento.model.Meta;
import br.edu.ifpe.recife.planejamento.model.Metrica;
import br.edu.ifpe.recife.planejamento.model.Objetivo;
import br.edu.ifpe.recife.planejamento.repository.DepartamentoRepository;
import br.edu.ifpe.recife.planejamento.repository.MetaRepository;
import br.edu.ifpe.recife.planejamento.repository.MetricaRepository;
import br.edu.ifpe.recife.planejamento.repository.ObjetivoRepository;

// Rotas de meta.
@RestController
@RequestMapping("/metas")
public class MetaController {

    private final MetaRepository repository;
    private final ObjetivoRepository objetivoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final MetricaRepository metricaRepository;

    public MetaController(MetaRepository repository, ObjetivoRepository objetivoRepository,
                          DepartamentoRepository departamentoRepository, MetricaRepository metricaRepository) {
        this.repository = repository;
        this.objetivoRepository = objetivoRepository;
        this.departamentoRepository = departamentoRepository;
        this.metricaRepository = metricaRepository;
    }

    @GetMapping
    public List<Meta> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meta> buscar(@PathVariable Long id) {
        Meta meta = repository.buscar(id);
        if (meta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meta);
    }

    // setId(null) garante que é um registro novo. Se o pai não existir, responde 400.
    @PostMapping
    public ResponseEntity<Meta> criar(@RequestBody Meta meta) {
        meta.setId(null);
        if (!carregarRelacionamentos(meta)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.salvar(meta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meta> atualizar(@PathVariable Long id, @RequestBody Meta meta) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        meta.setId(id);
        if (!carregarRelacionamentos(meta)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.salvar(meta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        repository.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // Lista as métricas de uma meta.
    @GetMapping("/{id}/metricas")
    public List<Metrica> listarMetricas(@PathVariable Long id) {
        return metricaRepository.listarPorMeta(id);
    }

    // O JSON traz os pais só com o id. Aqui busca os pais de verdade e coloca na meta.
    // Objetivo é obrigatório. Departamento é opcional, mas se vier precisa existir.
    private boolean carregarRelacionamentos(Meta meta) {
        if (meta.getObjetivo() == null || meta.getObjetivo().getId() == null) {
            return false;
        }
        Objetivo objetivo = objetivoRepository.buscar(meta.getObjetivo().getId());
        if (objetivo == null) {
            return false;
        }
        meta.setObjetivo(objetivo);
        if (meta.getDepartamento() != null && meta.getDepartamento().getId() != null) {
            Departamento departamento = departamentoRepository.buscar(meta.getDepartamento().getId());
            if (departamento == null) {
                return false;
            }
            meta.setDepartamento(departamento);
        }
        return true;
    }
}
