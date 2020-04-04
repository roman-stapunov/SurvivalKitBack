package com.epam.survival.kit.data;

import com.epam.survival.kit.config.HiberConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

    private final Environment environment;
    private final HiberConfig hiberConfig;

    public HibernateConfiguration(Environment environment, HiberConfig config) {
        this.environment = environment;
        this.hiberConfig = config;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("hikariSpringPoll");
        config.setDriverClassName(hiberConfig.getDriverClassName());
        config.setConnectionTestQuery("SELECT 1");
        config.setMaximumPoolSize(hiberConfig.getMaximumPoolSize());
        config.setIdleTimeout(hiberConfig.getIdleTimeout());
        config.setJdbcUrl(hiberConfig.getUrl());
        config.setUsername(hiberConfig.getUsername());
        config.setPassword(hiberConfig.getPassword());
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(hibernateProperties());
        entityManager.setPackagesToScan("org.egor.cathome.data.model");
        return entityManager;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hiberConfig.getHibernateDialect());
        properties.put("hibernate.show_sql", hiberConfig.isShowSql());
        properties.put("hibernate.format_sql", hiberConfig.isFormatSql());
        return properties;
    }

}
