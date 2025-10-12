package io.github.eduardoconceicao90.icompras.pedidos.controller.dto;

import io.github.eduardoconceicao90.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(
        String dados,
        TipoPagamento tipoPagamento
) {
}
