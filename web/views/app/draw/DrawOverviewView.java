package be.thibaulthelsmoortel.lotterymanagement.web.views.app.draw;

import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;

/**
 * DrawOverviewView view definition.
 *
 * @author Thibault Helsmoortel
 * @since 3/10/2017
 */
public interface DrawOverviewView extends LMView {

    interface DrawOverviewViewListener {

    }

    void addListener(DrawOverviewView.DrawOverviewViewListener listener);

    void addDrawToGrid(Draw draw);
}
