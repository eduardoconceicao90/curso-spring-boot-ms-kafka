package io.github.eduardoconceicao90.icompras.clientes.controller;

import io.github.eduardoconceicao90.icompras.clientes.model.Cliente;
import io.github.eduardoconceicao90.icompras.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.salvar(cliente));
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Cliente> obterPorCodigo(@PathVariable Long codigo) {
        return service.obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
