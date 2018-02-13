package be.thibaulthelsmoortel.lotterymanagement;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Spring boot servlet initializer.
 *
 * @author Thibault Helsmoortel
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LotteryManagementApplication.class);
    }
}
