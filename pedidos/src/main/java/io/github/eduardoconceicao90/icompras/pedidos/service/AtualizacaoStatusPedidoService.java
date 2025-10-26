package io.github.eduardoconceicao90.icompras.pedidos.service;

import io.github.eduardoconceicao90.icompras.pedidos.model.enums.StatusPedido;
import io.github.eduardoconceicao90.icompras.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;

    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String rastreio){

    }

}
