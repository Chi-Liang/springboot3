package com.springboot3.template.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component("projectConfig")
public class ProjectConfig {

    @Value("${project.path}")
    private String projectPath;

    @Value("${template.entry-point.auth}")
    private String templateAuth;

}
