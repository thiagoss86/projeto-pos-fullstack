package com.acme.cars.controller;

import com.acme.cars.model.Carro;
import com.acme.cars.payload.CriteriaRequest;
import com.acme.cars.service.CarroService;
import com.acme.cars.service.CsvService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carros")
@RequiredArgsConstructor
public class CarroController {
    private final CarroService carroService;
    private final CsvService csvService;


    @GetMapping("/search")
    public ResponseEntity<List<Carro>> search(
            @RequestParam(value = "modelo", required = false) Optional<String> modelo,
            @RequestParam(value = "fabricante", required = false) Optional<String> fabricante,
            @RequestParam(value = "pais", required = false) Optional<String> pais) {

        CriteriaRequest criteriaRequest = new CriteriaRequest(modelo, fabricante,pais);
        List<Carro> search = carroService.search(criteriaRequest);
        return ResponseEntity.ok(search);
    }
    @GetMapping
    public ResponseEntity<List<Carro>> listarTodos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(carroService.count()));
        List<Carro> allCarros = carroService.getAllPaginado(page, size);
        return ResponseEntity.ok().headers(headers).body(allCarros);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Carro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Carro> salvar(@Valid @RequestBody Carro carro) {
        Carro carroSalvo = carroService.salvar(carro);
        return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizar(@PathVariable Long id, @Valid @RequestBody Carro carroAtualizado) {
        return ResponseEntity.ok(carroService.atualizar(id, carroAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/export-cars")
    public ResponseEntity<FileSystemResource> exportCharacters() {
        String filePath = "carros.csv";
        csvService.generate(filePath);
        File file = new File(filePath);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + file.getName())
                .body(fileSystemResource);
    }
}
