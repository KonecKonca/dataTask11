package com.epam.kozitski.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {
    private static final String DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
    private static final int POOL_SIZE = 20;

    private static final String SERVER_NAME_PARAMETER = "serverName";
    private static final String SERVER_PORT_PARAMETER = "port";
    private static final String SERVER_URL_PARAMETER = "url";
    private static final String SERVER_USER_PARAMETER = "user";
    private static final String SERVER_PASSWORD_PARAMETER = "password";

    private static final String HOST = "localhost";
    private static final String PORT = "1521";
    private static final String URL = "jdbc:mysql://localhost:3306/data1";
    private static final String LOGIN = "root";
    private static final String PASS = "123ghu475R7px6";

    @Bean("hikariDataSource")
    public HikariDataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();

        ds.setMaximumPoolSize(POOL_SIZE);
        ds.setDataSourceClassName(DATA_SOURCE_CLASS_NAME);

        ds.addDataSourceProperty(SERVER_NAME_PARAMETER, HOST);
        ds.addDataSourceProperty(SERVER_PORT_PARAMETER, PORT);

        ds.addDataSourceProperty(SERVER_URL_PARAMETER, URL);
        ds.addDataSourceProperty(SERVER_USER_PARAMETER, LOGIN);
        ds.addDataSourceProperty(SERVER_PASSWORD_PARAMETER, PASS);

        return ds;
    }

    @Bean()
    public JdbcTemplate getJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }

}
