package io.github.eduardoconceicao90.icompras.pedidos.controller;

import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.controller.mapper.PedidoMapper;
import io.github.eduardoconceicao90.icompras.pedidos.model.exception.ErrorResponse;
import io.github.eduardoconceicao90.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.eduardoconceicao90.icompras.pedidos.model.exception.ValidationException;
import io.github.eduardoconceicao90.icompras.pedidos.publisher.mapper.DetalhePedidoMapper;
import io.github.eduardoconceicao90.icompras.pedidos.publisher.representation.DetalhePedidoRepresentation;
import io.github.eduardoconceicao90.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO novoPedidoDto) {
        try{
            var pedido = service.criar(mapper.toPedido(novoPedidoDto));
            return ResponseEntity.ok(pedido.getCodigo());
        } catch (ValidationException e) {
            var errorResponse = new ErrorResponse("Erro de validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO adicaoNovoPagamentoDTO) {
        try{
            service.adicionarNovoPagamento(
                    adicaoNovoPagamentoDTO.codigoPedido(),
                    adicaoNovoPagamentoDTO.dados(),
                    adicaoNovoPagamentoDTO.tipoPagamento()
            );
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
            var errorResponse = new ErrorResponse("Pedido não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable Long codigo) {
        return service
                .carregarDadosCompletosPedido(codigo)
                .map(detalhePedidoMapper::toDetalhePedidoRepresentation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
