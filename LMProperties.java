package be.thibaulthelsmoortel.lotterymanagement;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Thibault Helsmoortel
 * @since 13 Sep 2017
 */
@ConfigurationProperties(prefix = "lm")
@Data
public class LMProperties {

    private Environment environment;
    private Database db;
    private Currency currency;

    @Data
    public static class Environment {

        private String name;
    }

    @Data
    public static class Database {

        private String url;
        private String username;
        private String password;
    }

    @Data
    public static class Currency {

        private String symbol;
        private String code;
    }

}
