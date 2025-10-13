package io.github.eduardoconceicao90.icompras.pedidos.service;

import io.github.eduardoconceicao90.icompras.pedidos.client.ServiceBancarioClient;
import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import io.github.eduardoconceicao90.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.eduardoconceicao90.icompras.pedidos.repository.PedidoRepository;
import io.github.eduardoconceicao90.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServiceBancarioClient serviceBancarioClient;

    @Transactional
    public Pedido criar(Pedido pedido) {
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = serviceBancarioClient.solicatarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

}
