package be.thibaulthelsmoortel.lotterymanagement.web.views.app.player;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.web.AppUI;
import be.thibaulthelsmoortel.lotterymanagement.web.components.player.PlayerGrid;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.player.PlayerOverviewPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.AppPage;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Actual PlayerOverview view.
 *
 * @author Koen Rombout
 * @since 5/10/2017
 */

@SpringView(name = PlayerOverviewViewImpl.VIEW_NAME, ui = AppUI.class)
public class PlayerOverviewViewImpl extends AppPage implements PlayerOverviewView, View {

    public static final String VIEW_NAME = "playerOverview";
    private final transient PlayerOverviewPresenter presenter;
    private final transient List<PlayerOverviewViewListener> listeners = new ArrayList<>();

    private final PlayerGrid playerGrid;

    @Autowired
    public PlayerOverviewViewImpl(PlayerOverviewPresenter presenter, AppDelegate appDelegate) {
        super(appDelegate);
        this.presenter = presenter;
        presenter.init(this);

        setTitle(getMessage("playerOverview.title"));

        playerGrid = new PlayerGrid(presenter.getAllPlayers());
        playerGrid.setSizeFull();
        addComponent(playerGrid);
    }

    @Override
    public void addListener(PlayerOverviewViewListener listener) {
        listeners.add(listener);
    }
}
