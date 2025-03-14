package com.example.stockapp1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    @Value("${postgres://u5c0s1khcmcbbr:pa6ea03ac27a94a13b3c27e9184255afe6600bc4b557a10683eedad5b65c6a9b3@c3l5o0rb2a6o4l.cluster-czz5s0kz4scl.eu-west-1.rds.amazonaws.com:5432/d5n4rl379nuvev}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        // Exemplu URL: postgres://u5c0s1khcmcbbr:pa6ea03ac27a94a13b3c27e9184255afe6600bc4b557a10683eedad5b65c6a9b3@c3l5o0rb2a6o4l.cluster-czz5s0kz4scl.eu-west-1.rds.amazonaws.com:5432/d5n4rl379nuvev
        URI dbUri = new URI(databaseUrl);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];

        String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() 
                         + "?sslmode=require";

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }
}
