package io.github.eduardoconceicao90.icompras.pedidos.validator;

import io.github.eduardoconceicao90.icompras.pedidos.client.ClientesClient;
import io.github.eduardoconceicao90.icompras.pedidos.client.ProdutosClient;
import io.github.eduardoconceicao90.icompras.pedidos.model.ItemPedido;
import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {

    }

    private void validarItem(ItemPedido itemPedido) {

    }

}
