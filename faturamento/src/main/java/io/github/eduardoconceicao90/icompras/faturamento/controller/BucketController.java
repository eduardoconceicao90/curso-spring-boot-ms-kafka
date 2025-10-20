package io.github.eduardoconceicao90.icompras.faturamento.controller;

import io.github.eduardoconceicao90.icompras.faturamento.bucket.BucketFile;
import io.github.eduardoconceicao90.icompras.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("bucket")
public class BucketController {

    private final BucketService service;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {

            MediaType mediaType = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getOriginalFilename(), inputStream, mediaType, file.getSize());
            service.upload(bucketFile);

            return ResponseEntity.ok("Arquivo enviado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao fazer upload do arquivo: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam("filename") String filename) {
        try {
            String url = service.getUrl(filename);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao obter URL do arquivo: " + e.getMessage());
        }
    }

}
