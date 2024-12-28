package com.springboot3.template.utils;

import com.springboot3.template.service.HttpService;
import com.springboot3.template.service.SampleService;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcTools {
    private static String authEntryPoint;

    @Value("${template.entry-point.auth}")
    public void setAuthEntryPoint(String authEntryPoint) {
        MvcTools.authEntryPoint = authEntryPoint;
    }

    public static String redirectAuth(String path) {
        if (StringUtils.isNotBlank(path) && !StringUtils.startsWith(path, "/")) {
            path = "/" + path;
        }
        return "redirect:/" + MvcTools.authEntryPoint + StringUtils.defaultString(path);
    }
}
