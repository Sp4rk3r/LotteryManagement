package be.thibaulthelsmoortel.lotterymanagement.web.views.app.dashboard;

import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;

/**
 * Dashboard view definition
 *
 * @author Thibault Helsmoortel
 */
public interface DashboardView extends LMView {

    interface DashboardViewListener {

    }

    void addListener(DashboardViewListener listener);

}
