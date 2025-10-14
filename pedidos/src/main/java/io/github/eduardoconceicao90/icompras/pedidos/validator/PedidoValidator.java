package io.github.eduardoconceicao90.icompras.pedidos.validator;

import feign.FeignException;
import io.github.eduardoconceicao90.icompras.pedidos.client.ClientesClient;
import io.github.eduardoconceicao90.icompras.pedidos.client.ProdutosClient;
import io.github.eduardoconceicao90.icompras.pedidos.model.ItemPedido;
import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
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
        try {
            var response = clientesClient.obterPorCodigo(codigoCliente).getBody();
            log.info("Cliente com código {} encontrado: {}", response.codigo(), response.nome());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Cliente com código " + codigoCliente + " não encontrado");
        }
    }

    private void validarItem(ItemPedido itemPedido) {
        try {
            var response = produtosClient.obterPorCodigo(itemPedido.getCodigoProduto()).getBody();
            log.info("Produto com código {} encontrado: {}", response.codigo(), response.nome());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Produto com código " + itemPedido.getCodigoProduto() + " não encontrado");
        }
    }

}
