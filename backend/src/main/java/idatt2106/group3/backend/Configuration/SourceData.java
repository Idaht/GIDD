package idatt2106.group3.backend.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
/**
 * Configuration for Database
 */
@Configuration
public class SourceData {
    /**
     * Creates a MySQL datasource
     * Reads password and username from config.properties file
     * @return DataSource object
     */
    @Bean
    @Profile("dev")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSourceBuilder = new DriverManagerDataSource();
        
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/config.properties"))) {
            dataSourceBuilder.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSourceBuilder.setUrl(reader.readLine());
            dataSourceBuilder.setUsername(reader.readLine());
            dataSourceBuilder.setPassword(reader.readLine());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return dataSourceBuilder;
    }
}
