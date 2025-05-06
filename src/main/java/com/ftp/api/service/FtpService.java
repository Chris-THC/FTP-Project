package com.ftp.api.service;

import com.ftp.api.dto.FileInfoDto;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.ftp.api.assets.Time.getDateAndTime;

@Service
public class FtpService {

    private String host = "localhost";
    private int port = 21;
    private String user = "usuario1";
    private String pass = "1234";

    // Función para conectar y autenticar al servidor FTP
    private FTPClient loginFtp() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host, port);
        boolean loginSuccess = ftpClient.login(user, pass);

        if (!loginSuccess) {
            ftpClient.disconnect();
            throw new IOException("No se pudo iniciar sesión en el servidor FTP con las credenciales proporcionadas.");
        }

        ftpClient.enterLocalPassiveMode();
        return ftpClient;
    }

    // Lista los archivos y directorios en una ruta especificada
    public List<FileInfoDto> listFtpTree(String path) throws IOException {
        FTPClient ftpClient = loginFtp();
        List<FileInfoDto> result = new ArrayList<>();

        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(path);
            for (FTPFile ftpFile : ftpFiles) {
                FileInfoDto fileInfo = new FileInfoDto();
                String fullPath = "/" + Paths.get(path, ftpFile.getName()).toString().replace("\\", "/");
                fileInfo.setName(ftpFile.getName());
                fileInfo.setTimestamp(getDateAndTime(ftpFile.getTimestamp().getTimeInMillis()));
                fileInfo.setFile(ftpFile.isFile());
                fileInfo.setDirectory(ftpFile.isDirectory());
                fileInfo.setFullPath(fullPath);


                //Esta seccion muestra los archivos que se encuentran en directorios dentro del directorio principal
                //al que el usuario se encuentra
                if (ftpFile.isDirectory()) {
                    String subPath = Paths.get(path, ftpFile.getName()).toString();
                    fileInfo.setChildren(listFtpTree(subPath));
                }

                result.add(fileInfo);
            }
        } finally {
            logoutFtp(ftpClient); // Asegura que la conexión se cierre correctamente
        }

        return result;
    }

    // Crea un directorio en el servidor FTP
    public boolean createDirectory(String path) throws IOException {
        FTPClient ftpClient = loginFtp();
        boolean success = false;

        try {
            FTPFile[] files = ftpClient.listFiles(path);
            if (files.length == 0) {
                success = ftpClient.makeDirectory(path);
            }
        } finally {
            logoutFtp(ftpClient);
        }

        return success;
    }

    // Subir archivo al servidor FTP
    public boolean uploadFile(String localPath, String remotePath) throws IOException {
        FTPClient ftpClient = loginFtp();

        try (FileInputStream fileInputStream = new FileInputStream(localPath)) {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            return ftpClient.storeFile(remotePath, fileInputStream);
        } finally {
            logoutFtp(ftpClient);
        }
    }

    // Renombrar archivo en el servidor FTP
    public boolean renameFile(String oldPath, String newPath) throws IOException {
        FTPClient ftpClient = loginFtp();

        try {
            return ftpClient.rename(oldPath, newPath);
        } finally {
            logoutFtp(ftpClient);
        }
    }

    // Descargar archivo desde el servidor FTP
    public byte[] downloadFile(String path) throws IOException {
        FTPClient ftpClient = loginFtp();

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            boolean success = ftpClient.retrieveFile(path, byteArrayOutputStream);
            if (!success) {
                throw new IOException("Error al descargar el archivo: " + path);
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            logoutFtp(ftpClient);
        }
    }

    // Eliminar archivo en el servidor FTP
    public boolean deleteFile(String path) throws IOException {
        FTPClient ftpClient = loginFtp();

        try {
            return ftpClient.deleteFile(path);
        } finally {
            logoutFtp(ftpClient);
        }
    }

    // Eliminar directorio en el servidor FTP, incluyendo su contenido recursivamente
    public boolean deleteDirectory(String path) throws IOException {
        FTPClient ftpClient = loginFtp();
        try {
            return deleteDirectoryRecursive(ftpClient, path);
        } finally {
            logoutFtp(ftpClient);
        }
    }

    // Función auxiliar recursiva para eliminar todos los archivos y subdirectorios
    private boolean deleteDirectoryRecursive(FTPClient ftpClient, String path) throws IOException {
        FTPFile[] files = ftpClient.listFiles(path);

        for (FTPFile file : files) {
            String fullPath = path + "/" + file.getName();
            if (file.isDirectory()) {
                // Evitar borrar "." y ".."
                if (!file.getName().equals(".") && !file.getName().equals("..")) {
                    deleteDirectoryRecursive(ftpClient, fullPath);
                }
            } else {
                ftpClient.deleteFile(fullPath);
            }
        }

        // Finalmente eliminar el directorio vacío
        return ftpClient.removeDirectory(path);
    }


    // Cierra la conexión FTP de forma segura
    private void logoutFtp(FTPClient ftpClient) throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
