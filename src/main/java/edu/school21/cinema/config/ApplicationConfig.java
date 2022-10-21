package edu.school21.cinema.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.cinema.repositories.AuthenticationsRepositoryImpl;
import edu.school21.cinema.repositories.ImageRepositoryImpl;
import edu.school21.cinema.repositories.UsersRepositoryImpl;
import edu.school21.cinema.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@PropertySource("/application.properties")
public class ApplicationConfig {

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.driverClassName}")
    private String driverClassName;

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsersService usersService() {
        return new UsersServiceImpl(new UsersRepositoryImpl(hikariDataSource()), bCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationService authenticationService() {
        return new AuthenticationServiceImpl(new AuthenticationsRepositoryImpl(hikariDataSource()));
    }

    @Bean
    public ImageService imageService() {
        return new ImageServiceImpl(new ImageRepositoryImpl(hikariDataSource()));
    }
}
