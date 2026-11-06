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
import br.edu.ifpe.recife.planejamento.model.Meta;
import br.edu.ifpe.recife.planejamento.model.Metrica;
import br.edu.ifpe.recife.planejamento.repository.IndicadorRepository;
import br.edu.ifpe.recife.planejamento.repository.MetaRepository;
import br.edu.ifpe.recife.planejamento.repository.MetricaRepository;

// Rotas de métrica.
@RestController
@RequestMapping("/metricas")
public class MetricaController {

    private final MetricaRepository repository;
    private final MetaRepository metaRepository;
    private final IndicadorRepository indicadorRepository;

    public MetricaController(MetricaRepository repository, MetaRepository metaRepository,
                             IndicadorRepository indicadorRepository) {
        this.repository = repository;
        this.metaRepository = metaRepository;
        this.indicadorRepository = indicadorRepository;
    }

    @GetMapping
    public List<Metrica> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metrica> buscar(@PathVariable Long id) {
        Metrica metrica = repository.buscar(id);
        if (metrica == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metrica);
    }

    // setId(null) garante que é um registro novo. Se a meta não existir, responde 400.
    @PostMapping
    public ResponseEntity<Metrica> criar(@RequestBody Metrica metrica) {
        metrica.setId(null);
        if (!carregarRelacionamentos(metrica)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.salvar(metrica));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Metrica> atualizar(@PathVariable Long id, @RequestBody Metrica metrica) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        metrica.setId(id);
        if (!carregarRelacionamentos(metrica)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.salvar(metrica));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        repository.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // Lista os indicadores de uma métrica.
    @GetMapping("/{id}/indicadores")
    public List<Indicador> listarIndicadores(@PathVariable Long id) {
        return indicadorRepository.listarPorMetrica(id);
    }

    // O JSON traz o pai só com o id. Aqui busca a meta de verdade e coloca na métrica.
    private boolean carregarRelacionamentos(Metrica metrica) {
        if (metrica.getMeta() == null || metrica.getMeta().getId() == null) {
            return false;
        }
        Meta meta = metaRepository.buscar(metrica.getMeta().getId());
        if (meta == null) {
            return false;
        }
        metrica.setMeta(meta);
        return true;
    }
}
