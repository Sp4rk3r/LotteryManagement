package be.thibaulthelsmoortel.lotterymanagement.mappers;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

/**
 * Mapper for {@link be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment}.
 *
 * @author Thibault Helsmoortel
 */
@Component
@Mapper
public interface DrawEnrollmentMapper {

    @Select(" {CALL `p_create_draw_enrollment`("
            + "#{drawId, mode = IN, jdbcType=BIGINT},"
            + "#{playerId, mode = IN, jdbcType=BIGINT},"
            + "#{feePaid, mode = IN, jdbcType=BOOLEAN}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void createDrawEnrollment(CreateDrawEnrollmentRequest request);

    @Data
    class CreateDrawEnrollmentRequest {
        private long drawId;
        private long playerId;
        private boolean feePaid;
    }
}
