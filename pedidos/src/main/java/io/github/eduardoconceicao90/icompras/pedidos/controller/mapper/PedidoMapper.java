package io.github.eduardoconceicao90.icompras.pedidos.controller.mapper;

import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.model.ItemPedido;
import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import io.github.eduardoconceicao90.icompras.pedidos.model.enums.StatusPedido;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    // Instância do ItemPedidoMapper para reutilização
    ItemPedidoMapper ITEM_PEDIDO_MAPPER = getMapper(ItemPedidoMapper.class);

    // Mapeia NovoPedidoDTO para Pedido, utilizando o método mapItens para a lista de itens
    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    Pedido toPedido(NovoPedidoDTO novoPedidoDto);

    // Mapeia a lista de ItemPedidoDTO para uma lista de ItemPedido usando o ItemPedidoMapper
    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> itensDto) {
        return itensDto.stream()
                .map(ITEM_PEDIDO_MAPPER::toItemPedido)
                .toList();
    }

    // Método executado após o mapeamento para ajustar campos adicionais
    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        /*
           Calcula o total do pedido:
           1 - pedido.getItens().stream(): Cria um stream da lista de itens do pedido.
           2 - .map(item -> item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))): Para cada item, multiplica o valor unitário pela quantidade, resultando no valor total daquele item.
           3 - .reduce(BigDecimal.ZERO, BigDecimal::add): Soma todos os valores dos itens, começando do zero.
           4 - .abs(): Garante que o total seja positivo.
        */
        var total = pedido.getItens().stream().map(item ->
             item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();

        pedido.setTotal(total);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
    }

}
