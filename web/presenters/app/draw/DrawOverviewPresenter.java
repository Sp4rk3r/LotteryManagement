package be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.draw;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMErrorMessage;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMSuccessMessage;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.draw.DrawOverviewView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for {@link DrawOverviewView}.
 *
 * @author Thibault Helsmoortel
 * @since 3/10/2017
 */
@SpringComponent
@ViewScope
public class DrawOverviewPresenter implements DrawOverviewView.DrawOverviewViewListener {

    private final AppDelegate appDelegate;
    private DrawOverviewView view;

    @Autowired
    public DrawOverviewPresenter(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
    }

    public void init(DrawOverviewView view) {
        if (this.view == null) {
            this.view = view;

            view.addListener(this);
        }
    }

    // TODO: 13/10/2017 The draw overview should not retrieve all draws at once
    public List<Draw> getAllDraws() {
        try {
            return appDelegate.getAllDraws();
        } catch (WebBaseException e) {
            view.showGeneralError(LMView.ErrorType.READ);
            return new ArrayList<>();
        }
    }

    public void createDraw(String name, LocalDate drawDate, Integer enrollmentCapacity, BigDecimal enrollmentFee, BigDecimal maxWin) {
        Draw draw = new Draw();
        draw.setName(name);
        draw.setDrawDate(Date.valueOf(drawDate));
        draw.setEnrollmentCapacity(enrollmentCapacity);
        draw.setEnrollmentFee(enrollmentFee);
        draw.setMaxPossibleWin(maxWin);

        try {
            appDelegate.createDraw(draw);
            view.addDrawToGrid(draw);

            LMSuccessMessage lmSuccessMessage = new LMSuccessMessage(
                    getMessage("draw.add.success.title"),
                    getMessage("draw.add.success.content")
            );
            lmSuccessMessage.show();
        } catch (WebBaseException e) {
            LMErrorMessage lmErrorMessage = new LMErrorMessage(
                    getMessage("draw.add.error.title"),
                    getMessage("draw.add.error.content")
            );
            lmErrorMessage.show();
        }
    }
}
