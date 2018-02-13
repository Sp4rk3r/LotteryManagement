package be.thibaulthelsmoortel.lotterymanagement.config;

import be.thibaulthelsmoortel.lotterymanagement.LMProperties;
import be.thibaulthelsmoortel.lotterymanagement.LMProperties.Currency;
import be.thibaulthelsmoortel.lotterymanagement.LMProperties.Database;
import be.thibaulthelsmoortel.lotterymanagement.LMProperties.Environment;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Properties configuration.
 *
 * @author Thibault Helsmoortel
 */
@Configuration
@EnableConfigurationProperties({LMProperties.class})
@Data
public class PropertiesConfig {

    private final LMProperties properties;

    @Autowired
    public PropertiesConfig(LMProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Environment getEnvironment() {
        return properties.getEnvironment();
    }

    @Bean
    public Database getDatabase() {
        return properties.getDb();
    }

    @Bean
    public Currency getCurrency() {
        return properties.getCurrency();
    }
}
