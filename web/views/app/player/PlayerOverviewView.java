package be.thibaulthelsmoortel.lotterymanagement.web.views.app.player;

import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;
/**
 * PlayerOverviewView view definition
 * @author Koen Rombout
 * @since 5/10/2017
 */
public interface PlayerOverviewView extends LMView {

    interface PlayerOverviewViewListener {

    }

    void addListener (PlayerOverviewView.PlayerOverviewViewListener listener);

}
