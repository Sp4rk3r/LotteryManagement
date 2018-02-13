package be.thibaulthelsmoortel.lotterymanagement;

import be.thibaulthelsmoortel.lotterymanagement.LMProperties.Environment;
import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.AppStartup;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Class performing application startup tasks.
 *
 * @author Thibault Helsmoortel
 */
@Component
@Log4j2
public class ApplicationStartup implements ApplicationListener {

    private boolean appStarted;

    private final Environment environment;
    private final AppDelegate appDelegate;

    @Autowired
    public ApplicationStartup(Environment environment, AppDelegate appDelegate) {
        this.appStarted = false;
        this.environment = environment;
        this.appDelegate = appDelegate;
    }

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (!appStarted) {
            log.info("Starting application in environment: {}.", environment.getName());
            logStartup();
            appStarted = true;
        }
    }

    private void logStartup() {
        AppStartup appStartup = new AppStartup();
        appStartup.setStartupDate(new Date());
        appStartup.setEnvironment(environment.getName());
        try {
            appDelegate.saveAppStartup(appStartup);
        } catch (WebBaseException e) {
            log.error(e.getMessage(), e);
        }
    }
}
