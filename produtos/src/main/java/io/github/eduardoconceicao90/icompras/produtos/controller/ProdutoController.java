package io.github.eduardoconceicao90.icompras.produtos.controller;

import io.github.eduardoconceicao90.icompras.produtos.model.Produto;
import io.github.eduardoconceicao90.icompras.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        return ResponseEntity.ok(service.salvar(produto));
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Produto> obterPorCodigo(@PathVariable Long codigo) {
        return service.obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
        var produto = service.obterPorCodigo(codigo)
                             .orElseThrow(() -> new ResponseStatusException(
                                     HttpStatus.NOT_FOUND, "Produto não encontrado"
                             ));

        service.deletar(produto);
        return ResponseEntity.noContent().build();
    }

}
