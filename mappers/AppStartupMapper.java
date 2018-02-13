package be.thibaulthelsmoortel.lotterymanagement.mappers;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Mapper for {@link be.thibaulthelsmoortel.lotterymanagement.model.AppStartup}.
 *
 * @author Thibault Helsmoortel
 */
@Component
@Mapper
public interface AppStartupMapper {

    @Select(" {CALL `p_save_startup`("
            + "#{startupDate, mode = IN, jdbcType=TIMESTAMP},"
            + "#{environment, mode = IN, jdbcType=VARCHAR}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void saveStartup(SaveStartupRequest request);

    @Data
    class SaveStartupRequest {
        private Date startupDate;
        private String environment;
    }

}
