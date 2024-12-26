package com.springboot3.template.config;

import com.springboot3.template.service.HttpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpExchangeAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpConfiguration {

    @Bean
    public HttpServiceProxyFactory proxyFactory(){

        WebClient webClient = WebClient.builder()
                .build();

        WebClientAdapter clientAdapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(clientAdapter).build();
        return proxyFactory;
    }

    @Bean
    public HttpService httpService(HttpServiceProxyFactory proxyFactory){
        return  proxyFactory.createClient(HttpService.class);
    }

}
