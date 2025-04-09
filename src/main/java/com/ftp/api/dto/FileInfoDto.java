package com.ftp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto {
    private String name;
    private String fullPath;
    private String timestamp;
    private boolean isFile;
    private boolean isDirectory;
    @Builder.Default
    private List<FileInfoDto> children = new ArrayList<>();
}
