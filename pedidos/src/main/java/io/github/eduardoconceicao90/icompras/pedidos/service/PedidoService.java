package io.github.eduardoconceicao90.icompras.pedidos.service;

import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import io.github.eduardoconceicao90.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.eduardoconceicao90.icompras.pedidos.repository.PedidoRepository;
import io.github.eduardoconceicao90.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;

    public Pedido criar(Pedido pedido) {
        return null;
    }

}
