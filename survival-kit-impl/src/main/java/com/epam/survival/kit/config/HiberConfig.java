package com.epam.survival.kit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EnableConfigurationProperties
@Configuration
@Validated
@ConfigurationProperties("app.hibernate")
public class HiberConfig {

    @NotNull
    private String url;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String driverClassName;

    @Min(1)
    private int maximumPoolSize;

    @Min(5000)
    private int idleTimeout;

    @NotNull
    private String hibernateDialect;

    @NotNull
    private boolean showSql;

    @NotNull
    private boolean formatSql;
}
