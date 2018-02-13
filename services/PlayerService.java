package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import java.util.List;

/**
 * Service interface for {@link be.thibaulthelsmoortel.lotterymanagement.model.Player}.
 *
 * @author Koen Rombout
 * @since 5/10/2017
 */
public interface PlayerService {

    List<Player> getAllPlayers();
}
