package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/poster")
    public ResponseEntity<?> uploadPoster(@RequestParam("file") MultipartFile file) {
        try {

            String filePath = storageService.store(file);

            return ResponseEntity.ok(Map.of("filePath", filePath));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Falha no upload do poster: " + e.getMessage());
        }
    }
}