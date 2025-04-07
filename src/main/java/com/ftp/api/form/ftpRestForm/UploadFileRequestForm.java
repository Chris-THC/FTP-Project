package com.ftp.api.form.ftpRestForm;

import lombok.Data;

@Data
public class UploadFileRequestForm {
    private String localPath;
    private String remotePath;
}
