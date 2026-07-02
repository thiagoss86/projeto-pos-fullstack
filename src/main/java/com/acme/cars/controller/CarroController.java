package com.acme.cars.controller;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;
import com.acme.cars.service.CarroService;
import com.acme.cars.service.CsvService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final CarroService carroServiceImpl;
    private final CsvService csvServiceImpl;


    @GetMapping("/search")
    public ResponseEntity<List<Carro>> search(
            @RequestParam(value = "modelo", required = false) String modelo,
            @RequestParam(value = "fabricante", required = false) String fabricante,
            @RequestParam(value = "pais", required = false) String pais) {

        BuscarCarroRequest buscarCarroRequest = new BuscarCarroRequest(
                Optional.ofNullable(modelo),
                Optional.ofNullable(fabricante),
                Optional.ofNullable(pais));

        List<Carro> search = carroServiceImpl.buscar(buscarCarroRequest);
        return ResponseEntity.ok(search);
    }
    @GetMapping
    public ResponseEntity<List<Carro>> listarTodos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(carroServiceImpl.count()));
        List<Carro> allCarros = carroServiceImpl.buscarTodosPaginado(page, size);
        return ResponseEntity.ok().headers(headers).body(allCarros);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Carro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carroServiceImpl.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Carro> salvar(@Valid @RequestBody Carro carro) {
        Carro carroSalvo = carroServiceImpl.salvar(carro);
        return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizar(@PathVariable Long id, @Valid @RequestBody Carro carroAtualizado) {
        return ResponseEntity.ok(carroServiceImpl.atualizar(id, carroAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carroServiceImpl.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/export-cars")
    public ResponseEntity<FileSystemResource> exportCharacters() {
        String filePath = "carros.csv";
        csvServiceImpl.gerarArquivo(filePath);
        File file = new File(filePath);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + file.getName())
                .body(fileSystemResource);
    }
}
