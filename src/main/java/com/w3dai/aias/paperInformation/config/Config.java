package com.w3dai.aias.paperInformation.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.elasticsearch.transport.Netty4Plugin;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.w3dai.aias.paperInformation.entity")
@ComponentScan(basePackages = { "com.w3dai.aias.paperInformation"})
public class Config {
    @Value("D:\\elasticsearch\\5.6.0")
    private String elasticsearchHome;

    @Value("my-application")
    private String clusterName;

    @Bean
    public Client client() throws Exception{
        Settings elasticsearchSettings = Settings.builder().put("client.transport.sniff", true).put("path.home", elasticsearchHome).put("cluster.name", clusterName).build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate()  throws Exception{
        return new ElasticsearchTemplate(client());
    }
}