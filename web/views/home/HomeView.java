package be.thibaulthelsmoortel.lotterymanagement.web.views.home;

import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;

/**
 * Home view definition.
 *
 * @author Thibault Helsmoortel
 */
public interface HomeView extends LMView {

    interface HomeViewListener {

    }

    void addListener(HomeViewListener listener);

}
