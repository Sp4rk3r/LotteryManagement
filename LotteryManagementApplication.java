package be.thibaulthelsmoortel.lotterymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application entry point.
 *
 * @author Thibault Helsmoortel
 */
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class LotteryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotteryManagementApplication.class, args);
    }
}
