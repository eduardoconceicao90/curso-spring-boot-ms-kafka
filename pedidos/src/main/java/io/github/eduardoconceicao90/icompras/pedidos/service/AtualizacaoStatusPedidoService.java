package io.github.eduardoconceicao90.icompras.pedidos.service;

import io.github.eduardoconceicao90.icompras.pedidos.model.enums.StatusPedido;
import io.github.eduardoconceicao90.icompras.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;

    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String rastreio){
        repository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(status != null ? status : pedido.getStatus());
            pedido.setUrlNotaFiscal(urlNotaFiscal != null ? urlNotaFiscal : pedido.getUrlNotaFiscal());
            pedido.setCodigoRastreio(rastreio != null ? rastreio : pedido.getCodigoRastreio());
        });
    }

}
