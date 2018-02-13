package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.mappers.PlayerMapper;
import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service for {@link be.thibaulthelsmoortel.lotterymanagement.model.Player}.
 *
 * @author Koen Rombout
 * @since 5/10/2017
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerMapper.getAllPlayers();
    }
}
