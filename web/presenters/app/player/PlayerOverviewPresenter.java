package be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.player;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView.ErrorType;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.player.PlayerOverviewView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for {@link PlayerOverviewView}.
 *
 * @author Koen Rombout
 * @since 5/10/2017
 */
@SpringComponent
@ViewScope
public class PlayerOverviewPresenter implements PlayerOverviewView.PlayerOverviewViewListener {

    private final AppDelegate appDelegate;
    private PlayerOverviewView view;

    @Autowired
    public PlayerOverviewPresenter(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
    }

    public void init(PlayerOverviewView view) {
        if (this.view == null) {
            this.view = view;

            view.addListener(this);
        }
    }

    // TODO: 13/10/2017 The player overview should not retrieve all players at once
    public List<Player> getAllPlayers() {
        try {
            return appDelegate.getAllPlayers();
        } catch (WebBaseException e) {
            view.showGeneralError(ErrorType.READ);
            return new ArrayList<>();
        }
    }
}
