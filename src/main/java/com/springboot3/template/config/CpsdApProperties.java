package com.springboot3.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cpsd.ap")
public class CpsdApProperties {

    private String decryptKey;
    private String salt;

}
