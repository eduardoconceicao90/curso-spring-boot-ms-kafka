package io.github.eduardoconceicao90.icompras.faturamento.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eduardoconceicao90.icompras.faturamento.model.Pedido;
import io.github.eduardoconceicao90.icompras.faturamento.model.enums.StatusPedido;
import io.github.eduardoconceicao90.icompras.faturamento.publisher.representation.AtualizacaoStatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FaturamentoPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-faturados}")
    private String topicoFaturados;

    public void publicar(Pedido pedido, String urlNotaFiscal) {
        try {
            var representation = new AtualizacaoStatusPedido(pedido.codigo(), StatusPedido.FATURADO, urlNotaFiscal);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topicoFaturados, "dados", json);
            log.info("Evento de pedido faturado publicado com sucesso para o pedido de código: {}", pedido.codigo());
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter o evento de pedido faturado para JSON: {}", pedido.codigo());
        } catch (RuntimeException e){
            log.error("Erro técnico ao publicar no tópido de faturados", e);
        }
    }

}
