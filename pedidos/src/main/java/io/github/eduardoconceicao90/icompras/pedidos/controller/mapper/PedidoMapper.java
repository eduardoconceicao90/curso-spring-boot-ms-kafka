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

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = getMapper(ItemPedidoMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    Pedido toPedido(NovoPedidoDTO novoPedidoDto);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> itensDto) {
        return itensDto.stream()
                .map(ITEM_PEDIDO_MAPPER::toItemPedido)
                .toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = pedido.getItens().stream().map(item ->
             item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();

        pedido.setTotal(total);
    }

}
