package be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.player;

import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import java.util.List;

/**
 * Functional interface defining a presenter that is able to return a list of all {@link Player} instances.
 *
 * @author Thibault Helsmoortel
 */
@FunctionalInterface
public interface GetAllPlayersPresentable {

    List<Player> getAllPlayers() throws WebBaseException;

}
