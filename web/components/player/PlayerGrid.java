package be.thibaulthelsmoortel.lotterymanagement.web.components.player;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import com.vaadin.ui.Grid;
import java.util.List;
/**
 * Grid for displaying {@link be.thibaulthelsmoortel.lotterymanagement.model.Player} elements.
 *
 * @author Koen Rombout
 * @since 5/10/2017
 */
public class PlayerGrid extends Grid<Player> {

    public PlayerGrid(List<Player> players) {
        setItems(players);
        addColumn(Player::getFirstName).setCaption(getMessage("playerOverview.grid.firstName"));
        addColumn(Player::getLastName).setCaption(getMessage("playerOverview.grid.lastName"));
    }

    public Player getSelectedPlayer() {
        return getSelectedItems().stream().findFirst().orElse(null);
    }
}
