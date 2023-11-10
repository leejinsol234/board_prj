package org.board.project.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix="file.upload")
@Data
public class FileUploadConfig {
    private String path; //file.upload.path
    private String url; //file.upload.url
}
