package be.thibaulthelsmoortel.lotterymanagement.mappers;

import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import java.util.List;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

/**
 * Mapper for {@link be.thibaulthelsmoortel.lotterymanagement.model.Player}.
 *
 * @author Koen Rombout
 * @since 5/10/2017
 */
@Component
@Mapper
public interface PlayerMapper {

    @Select(" {CALL `p_get_all_players`()}")
    @Options(statementType =  StatementType.CALLABLE)
    List<Player> getAllPlayers();

    @Data
    class GetPlayerRequest{
        private Long playerId;
    }

}
