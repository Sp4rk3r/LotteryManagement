package be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.drawenrollments;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment;
import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMErrorMessage;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMSuccessMessage;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Presenter for {@link be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment}.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class DrawEnrollmentsPresenter {

    private final AppDelegate appDelegate;

    @Autowired
    public DrawEnrollmentsPresenter(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
    }

    public void createDrawEnrollments(Draw draw, List<Player> players, boolean feePaid) {
        try {
            for(Player player : players) {
                DrawEnrollment enrollment = new DrawEnrollment();
                enrollment.setDraw(draw);
                enrollment.setPlayer(player);
                enrollment.setFeePaid(feePaid);
                appDelegate.createDrawEnrollment(enrollment);
                LMSuccessMessage lmSuccessMessage = new LMSuccessMessage(
                        getMessage("drawEnrollment.add.success.title"),
                        getMessage("drawEnrollment.add.success.content")
                );
                lmSuccessMessage.show();
            }
        } catch (WebBaseException e) {
            LMErrorMessage lmErrorMessage = new LMErrorMessage(
                    getMessage("drawEnrollment.add.error.title"),
                    getMessage("drawEnrollment.add.error.content")
            );
            lmErrorMessage.show();
        }
    }
}
