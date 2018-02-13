package be.thibaulthelsmoortel.lotterymanagement.model;

import java.util.Date;
import lombok.Data;

/**
 * Object containing data gathered during application startup.
 *
 * @author Thibault Helsmoortel
 */
@Data
public class AppStartup {

    private Date startupDate;
    private String environment;
}
