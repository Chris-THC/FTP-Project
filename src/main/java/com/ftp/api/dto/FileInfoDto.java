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
    private long timestamp;
    private String group;
    private String link;
    private String user;
    private int type;
    private boolean isFile;
    private boolean isDirectory;
    private String formattedString;
    @Builder.Default
    private List<FileInfoDto> children = new ArrayList<>();
}
