package be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.dashboard;

import be.thibaulthelsmoortel.lotterymanagement.web.views.app.dashboard.DashboardView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import java.io.Serializable;

/**
 * Presenter for {@link DashboardView}.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class DashboardPresenter implements DashboardView.DashboardViewListener {

    private DashboardView view;

    public void init(DashboardView view) {
        if (this.view == null) {
            this.view = view;

            view.addListener(this);
        }
    }

}
