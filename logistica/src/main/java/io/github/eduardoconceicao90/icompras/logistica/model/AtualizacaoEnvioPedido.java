package io.github.eduardoconceicao90.icompras.logistica.model;

import io.github.eduardoconceicao90.icompras.logistica.model.enums.StatusPedido;

public record AtualizacaoEnvioPedido(
        Long codigo,
        StatusPedido status,
        String codigoRastreio
) {
}
