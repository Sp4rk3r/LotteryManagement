package be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.player;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import java.util.List;

/**
 * Presenter of {@link Player} objects meant to be used only in autocomplete implementations.
 *
 * Note that this presenter does not have a view in scope (no view field), so exceptions will have to be handled in the
 * view.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class AutoCompletePlayerPresenter implements GetAllPlayersPresentable {

    private final AppDelegate appDelegate;

    public AutoCompletePlayerPresenter(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
    }

    @Override
    public List<Player> getAllPlayers() throws WebBaseException {
        return appDelegate.getAllPlayers();
    }
}
