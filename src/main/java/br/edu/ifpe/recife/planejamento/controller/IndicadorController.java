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

import br.edu.ifpe.recife.planejamento.model.Indicador;
import br.edu.ifpe.recife.planejamento.model.Metrica;
import br.edu.ifpe.recife.planejamento.repository.IndicadorRepository;
import br.edu.ifpe.recife.planejamento.repository.MetricaRepository;

// Rotas de indicador.
@RestController
@RequestMapping("/indicadores")
public class IndicadorController {

    private final IndicadorRepository repository;
    private final MetricaRepository metricaRepository;

    public IndicadorController(IndicadorRepository repository, MetricaRepository metricaRepository) {
        this.repository = repository;
        this.metricaRepository = metricaRepository;
    }

    @GetMapping
    public List<Indicador> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Indicador> buscar(@PathVariable Long id) {
        Indicador indicador = repository.buscar(id);
        if (indicador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(indicador);
    }

    // setId(null) garante que é um registro novo. Se a métrica não existir, responde 400.
    @PostMapping
    public ResponseEntity<Indicador> criar(@RequestBody Indicador indicador) {
        indicador.setId(null);
        if (!carregarRelacionamentos(indicador)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.salvar(indicador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Indicador> atualizar(@PathVariable Long id, @RequestBody Indicador indicador) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        indicador.setId(id);
        if (!carregarRelacionamentos(indicador)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.salvar(indicador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        repository.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // O JSON traz o pai só com o id. Aqui busca a métrica de verdade e coloca no indicador.
    private boolean carregarRelacionamentos(Indicador indicador) {
        if (indicador.getMetrica() == null || indicador.getMetrica().getId() == null) {
            return false;
        }
        Metrica metrica = metricaRepository.buscar(indicador.getMetrica().getId());
        if (metrica == null) {
            return false;
        }
        indicador.setMetrica(metrica);
        return true;
    }
}
