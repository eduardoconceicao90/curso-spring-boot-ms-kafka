package io.github.eduardoconceicao90.icompras.pedidos.controller;

import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import io.github.eduardoconceicao90.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody NovoPedidoDTO novoPedido) {
        return null;
    }

}
