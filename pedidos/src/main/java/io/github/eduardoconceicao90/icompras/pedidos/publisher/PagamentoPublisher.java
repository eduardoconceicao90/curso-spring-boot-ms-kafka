package io.github.eduardoconceicao90.icompras.pedidos.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import io.github.eduardoconceicao90.icompras.pedidos.publisher.mapper.DetalhePedidoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PagamentoPublisher {

    private final DetalhePedidoMapper mapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topicoPagamentos;

    public void publicar(Pedido pedido){
        log.info("Publicando evento de pedido pago para o pedido de código: {}", pedido.getCodigo());

        try {
            var representation = mapper.toDetalhePedidoRepresentation(pedido);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topicoPagamentos, "dados", json);
            log.info("Evento de pedido pago publicado com sucesso para o pedido de código: {}", pedido.getCodigo());
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter o evento de pedido pago para JSON: {}", pedido.getCodigo());
        } catch (RuntimeException e){
            log.error("Erro técnico ao publicar no tópido de pedidos", e);
        }
    }

}
