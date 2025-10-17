package io.github.eduardoconceicao90.icompras.pedidos.controller.dto;

import io.github.eduardoconceicao90.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(
        Long codigoPedido,
        String dados,
        TipoPagamento tipoPagamento
) {
}
