package io.github.eduardoconceicao90.icompras.pedidos.service;

import io.github.eduardoconceicao90.icompras.pedidos.client.ServiceBancarioClient;
import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import io.github.eduardoconceicao90.icompras.pedidos.model.enums.StatusPedido;
import io.github.eduardoconceicao90.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.eduardoconceicao90.icompras.pedidos.repository.PedidoRepository;
import io.github.eduardoconceicao90.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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

    public void atualizarStatusPagamento(
            Long codigoPedido, String chavePagamento, Boolean sucesso, String observacoes
    ) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if(pedidoEncontrado.isEmpty()) {
            var msg = String.format("Pedido com código %d e chave de pagamento %s não encontrado", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        } else {
            Pedido pedido = pedidoEncontrado.get();

            if(sucesso){
                pedido.setStatus(StatusPedido.PAGO);
                log.info("Pedido {} pago com sucesso.", pedido.getCodigo());
            } else {
                pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
                pedido.setObservacoes(observacoes);
                log.info("Pedido {} com erro no pagamento. Observações: {}", pedido.getCodigo(), observacoes);
            }

            repository.save(pedido);
        }
    }

}
