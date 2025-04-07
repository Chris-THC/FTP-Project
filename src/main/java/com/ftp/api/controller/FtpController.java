package com.ftp.api.controller;

import com.ftp.api.dto.FileInfoDto;
import com.ftp.api.form.ftpRestForm.*;
import com.ftp.api.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
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
    public ResponseEntity<String> uploadFile(@RequestBody UploadFileRequestForm request) {
        try {
            String remotePath = request.getRemotePath();

            // Si remotePath no tiene un archivo especificado, extraemos el nombre del archivo del localPath
            if (remotePath == null || !remotePath.contains(".")) {
                String fileName = new File(request.getLocalPath()).getName(); // Obtiene el nombre del archivo del localPath
                remotePath = remotePath + "/" + fileName; // Construye el remotePath completo con el nombre del archivo
            }

            boolean success = ftpService.uploadFile(request.getLocalPath(), remotePath);
            return success
                    ? ResponseEntity.ok("Archivo subido exitosamente")
                    : ResponseEntity.status(400).body("No se pudo subir el archivo");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir el archivo");
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

    @DeleteMapping("/delete")
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

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestBody DownloadFileRequestForm request) {
        try {
            byte[] fileData = ftpService.downloadFile(request.getPath());
            return ResponseEntity.ok(fileData);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
