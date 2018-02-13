package be.thibaulthelsmoortel.lotterymanagement.web.components.common;

import be.thibaulthelsmoortel.lotterymanagement.AppConstants;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import java.util.Calendar;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

/**
 * Footer component to be shown on every {@link be.thibaulthelsmoortel.lotterymanagement.web.views.LMPage}.
 *
 * @author Thibault Helsmoortel
 * @since 9/10/2017
 */
public class Footer extends HorizontalLayout {

    public Footer() {
        // String#valueOf to prevent number formatting in message source
        addComponent(new Label(getMessage("footer.copyright", String.valueOf(AppConstants.COPYRIGHT_START_YEAR), String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));
        addComponent(new Label(getMessage("footer.madeBy")));
        setPrimaryStyleName(LMTheme.FOOTER);
        setWidth(100, Unit.PERCENTAGE);
        setSpacing(true);
    }
}
