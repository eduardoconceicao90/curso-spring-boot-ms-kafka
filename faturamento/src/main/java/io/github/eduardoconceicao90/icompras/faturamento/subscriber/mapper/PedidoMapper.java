package io.github.eduardoconceicao90.icompras.faturamento.subscriber.mapper;

import io.github.eduardoconceicao90.icompras.faturamento.model.Cliente;
import io.github.eduardoconceicao90.icompras.faturamento.model.ItemPedido;
import io.github.eduardoconceicao90.icompras.faturamento.model.Pedido;
import io.github.eduardoconceicao90.icompras.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import io.github.eduardoconceicao90.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation representation) {
        Cliente cliente = new Cliente(
                representation.nome(),
                representation.cpf(),
                representation.logradouro(),
                representation.numero(),
                representation.bairro(),
                representation.email(),
                representation.telefone()
        );

        List<ItemPedido> itens = representation.itens().stream().map(this::mapItem).toList();
        return new Pedido(representation.codigo(), cliente, representation.dataPedido(), itens, representation.total());
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation representation) {
        return new ItemPedido(
                representation.codigoProduto(),
                representation.nome(),
                representation.valorUnitario(),
                representation.quantidade()
        );
    }

}
