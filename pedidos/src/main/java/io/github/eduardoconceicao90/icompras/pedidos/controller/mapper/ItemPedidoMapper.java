package io.github.eduardoconceicao90.icompras.pedidos.controller.mapper;

import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido toItemPedido(ItemPedidoDTO itemPedidoDto);

}
