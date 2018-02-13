package be.thibaulthelsmoortel.lotterymanagement.config;

import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tomcat configuration.
 *
 * @author Thibault Helsmoortel
 */
@Configuration
public class TomcatConfig {

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory();
    }
}
