package com.ftp.api.controller;

import com.ftp.api.dto.FileInfoDto;
import com.ftp.api.form.ftpRestForm.*;
import com.ftp.api.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/ftp")
public class FtpController {

    @Autowired
    private FtpService ftpService;

    @PostMapping("/folder/tree")
    public ResponseEntity<List<FileInfoDto>> listFtpTree(@RequestBody ListFtpTreeRequestForm request) {
        try {
            List<FileInfoDto> files = ftpService.listFtpTree(request.getPath());
            return ResponseEntity.ok(files);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "remotePath", required = false) String remotePath
    ) {
        try {
            String fileName = file.getOriginalFilename();
            if (remotePath == null || !remotePath.contains(".")) {
                // Si remotePath no tiene un nombre de archivo, lo añadimos
                remotePath = (remotePath == null ? "" : remotePath) + "/" + fileName;
            }

            try (InputStream inputStream = file.getInputStream()) {
                boolean success = ftpService.uploadFile(inputStream, remotePath);
                return success
                        ? ResponseEntity.ok("Archivo subido exitosamente")
                        : ResponseEntity.status(400).body("No se pudo subir el archivo");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir el archivo: " + e.getMessage());
        }
    }


    @PostMapping("/create/directory")
    public ResponseEntity<String> createDirectory(@RequestBody CreateDirectoryRequestForm request) {
        System.out.println("Path recibido: " + request.getPath());  // Para depuración
        try {
            boolean success = ftpService.createDirectory(request.getPath());
            return success
                    ? ResponseEntity.ok("Directorio creado exitosamente")
                    : ResponseEntity.status(400).body("El directorio ya existe");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al crear el directorio");
        }
    }


    @PutMapping("/rename")
    public ResponseEntity<String> renameFile(@RequestBody RenameFileRequestForm request) {
        try {
            boolean success = ftpService.renameFile(request.getOldPath(), request.getNewPath());
            return success
                    ? ResponseEntity.ok("Archivo renombrado exitosamente")
                    : ResponseEntity.status(400).body("No se pudo renombrar el archivo");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al renombrar el archivo");
        }
    }

    @DeleteMapping("/delete/file")
    public ResponseEntity<String> deleteFile(@RequestBody DeleteFileRequestForm request) {
        try {
            boolean success = ftpService.deleteFile(request.getPath());
            return success
                    ? ResponseEntity.ok("Archivo eliminado exitosamente")
                    : ResponseEntity.status(400).body("No se pudo eliminar el archivo");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al eliminar el archivo");
        }
    }

    @DeleteMapping("/delete/directory")
    public ResponseEntity<String> deleteDirectory(@RequestBody DeleteFileRequestForm request) {
        try {
            boolean success = ftpService.deleteDirectory(request.getPath());
            return success
                    ? ResponseEntity.ok("Carpeta borrada exitosamente")
                    : ResponseEntity.status(400).body("No se pudo eliminar la carpeta");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al eliminar la carpeta");
        }
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestBody DownloadFileRequestForm request) {
        try {
            byte[] fileData = ftpService.downloadFile(request.getPath());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + extractFileName(request.getPath()) + "\"")
                    .body(fileData);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Método auxiliar para extraer el nombre del archivo de la ruta
    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
