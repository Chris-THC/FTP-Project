package com.ftp.api.form.ftpRestForm;

import lombok.Data;

@Data
public class RenameFileRequestForm {
    private String oldPath;
    private String newPath;
}
