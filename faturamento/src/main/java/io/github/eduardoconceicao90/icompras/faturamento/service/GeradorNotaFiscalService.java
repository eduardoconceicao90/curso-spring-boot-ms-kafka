package io.github.eduardoconceicao90.icompras.faturamento.service;

import io.github.eduardoconceicao90.icompras.faturamento.bucket.BucketFile;
import io.github.eduardoconceicao90.icompras.faturamento.bucket.BucketService;
import io.github.eduardoconceicao90.icompras.faturamento.model.Pedido;
import io.github.eduardoconceicao90.icompras.faturamento.publisher.FaturamentoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class GeradorNotaFiscalService {

    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo.png")
    private Resource logo;

    private final BucketService bucketService;
    private final FaturamentoPublisher publisher;

    public void gerarNotaFiscal(Pedido pedido){
        log.info("Gerando nota fiscal para o pedido {} ", pedido.codigo());

        try {
            byte[] byteArray = gerarReportNotaFiscal(pedido);
            String nomeArquivo = String.format("nota_fiscal_pedido_%d.pdf", pedido.codigo());

            var file = new BucketFile(
                    nomeArquivo,
                    new ByteArrayInputStream(byteArray),
                    MediaType.APPLICATION_PDF,
                    byteArray.length
            );

            bucketService.upload(file);

            String url = bucketService.getUrl(nomeArquivo);
            publisher.publicar(pedido, url);

            log.info("Gerada nota fiscal, nome do arquivo: {}", nomeArquivo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public byte[] gerarReportNotaFiscal(Pedido pedido){
        try (InputStream inputStream = notaFiscal.getInputStream()) {
            Map<String, Object> params = new HashMap<>();

            params.put("NOME", pedido.cliente().nome());
            params.put("CPF", pedido.cliente().cpf());
            params.put("LOGRADOURO", pedido.cliente().logradouro());
            params.put("NUMERO", pedido.cliente().numero());
            params.put("BAIRRO", pedido.cliente().bairro());
            params.put("EMAIL", pedido.cliente().email());
            params.put("TELEFONE", pedido.cliente().telefone());
            params.put("DATA_PEDIDO", pedido.data());
            params.put("TOTAL_PEDIDO", pedido.total());
            params.put("LOGO", logo.getFile().getAbsolutePath());

            var dataSource = new JRBeanCollectionDataSource(pedido.itens());

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
