package be.thibaulthelsmoortel.lotterymanagement.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Viritin CDN config (for compiling widgetset).
 *
 * @author Thibault Helsmoortel
 * @since 13/10/2017
 */
@Configuration
@ServletComponentScan({"com.vaadin.wscdn"})
public class ViritinCdnConfig {

    // Stuff needed for widgetset CDN, not needed if using std widgetset.
    // Run priming build (mvn install) if (generated) resource is missing.
    // OR: vwscdn:generate (from plugin vwscdn)
    @Bean
    public in.virit.WidgetSet viritinCdnInitializer() {
        return new in.virit.WidgetSet();
    }
}
