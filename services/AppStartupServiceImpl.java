package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.mappers.AppStartupMapper;
import be.thibaulthelsmoortel.lotterymanagement.mappers.AppStartupMapper.SaveStartupRequest;
import be.thibaulthelsmoortel.lotterymanagement.model.AppStartup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for {@link AppStartup}.
 *
 * @author Thibault Helsmoortel
 */
@Service
public class AppStartupServiceImpl implements AppStartupService {

    private final AppStartupMapper mapper;

    @Autowired
    public AppStartupServiceImpl(AppStartupMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void saveAppStartup(AppStartup appStartup) {
        AppStartupMapper.SaveStartupRequest request = new SaveStartupRequest();
        request.setStartupDate(appStartup.getStartupDate());
        request.setEnvironment(appStartup.getEnvironment());
        mapper.saveStartup(request);
    }
}
