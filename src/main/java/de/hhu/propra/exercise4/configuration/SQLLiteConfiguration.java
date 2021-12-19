package de.hhu.propra.exercise4.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class SQLLiteConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    @Value("${spring.datasource.jdbcUrl}")
    String sqliteJdbcUrl;

    private SQLiteConfig setDefaultSettings(){
        Properties properties = new Properties();
        properties.put("auto_vacuum", "full");
        SQLiteConfig config = new SQLiteConfig(properties);
        config.setEncoding(SQLiteConfig.Encoding.UTF8);
        config.enforceForeignKeys(true);
        config.setJournalMode(SQLiteConfig.JournalMode.WAL);
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        return config;
    }

    @Bean
    public SQLiteDataSource createSQLiteDataSource(){
        SQLiteDataSource dataSource = new SQLiteDataSource(setDefaultSettings());
        try {
            // In theory we should hook this up to slf4j to marry logging to spring boots.
            dataSource.setLogWriter(new PrintWriter(System.out));
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        dataSource.setUrl("jdbc:sqlite:db" + File.separator + "database.db");
        return dataSource;
    }

}
