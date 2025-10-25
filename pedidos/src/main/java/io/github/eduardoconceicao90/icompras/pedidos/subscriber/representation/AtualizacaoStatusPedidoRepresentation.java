package io.github.eduardoconceicao90.icompras.pedidos.subscriber.representation;

import io.github.eduardoconceicao90.icompras.pedidos.model.enums.StatusPedido;

public record AtualizacaoStatusPedidoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal,
        String codigoRastreio
) {
}
