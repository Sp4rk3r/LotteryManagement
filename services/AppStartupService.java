package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.model.AppStartup;

/**
 * Service interface for {@link AppStartup}.
 *
 * @author Thibault Helsmoortel
 */
public interface AppStartupService {

    void saveAppStartup(AppStartup appStartup);
}
