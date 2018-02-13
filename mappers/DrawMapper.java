package be.thibaulthelsmoortel.lotterymanagement.mappers;

import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment;
import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for {@link be.thibaulthelsmoortel.lotterymanagement.model.Draw}.
 *
 * @author Thibault Helsmoortel
 * @since 3/10/2017
 */
@Component
@Mapper
public interface DrawMapper {

    @Select(" {CALL `p_get_all_draws`()}")
    @Options(statementType = StatementType.CALLABLE)
    List<Draw> getAllDraws();

    @Select(" {CALL `p_get_draw_enrollments`(#{drawId, mode = IN, jdbcType=BIGINT})}")
    @Options(statementType = StatementType.CALLABLE)
    List<DrawEnrollment> getDrawEnrollments(GetDrawEnrollmentsRequest request);

    @Select(" {CALL `p_get_draw`(#{drawId, mode = IN, jdbcType=BIGINT})}")
    @Options(statementType = StatementType.CALLABLE)
    Draw getDraw(GetDrawRequest request);

    @Select(" {CALL `p_get_draw_enrollment_player`(#{drawEnrollmentId, mode = IN, jdbcType=BIGINT})}")
    @Options(statementType = StatementType.CALLABLE)
    Player getDrawEnrollmentPlayer(GetDrawEnrollmentPlayerRequest request);

    @Select(" {CALL `p_create_draw`("
            + "#{name, mode = IN, jdbcType=VARCHAR},"
            + "#{drawDate, mode = IN, jdbcType=DATE},"
            + "#{enrollmentCapacity, mode = IN, jdbcType=INTEGER},"
            + "#{enrollmentFee, mode = IN, jdbcType=DECIMAL},"
            + "#{totalWon, mode = IN, jdbcType=DECIMAL},"
            + "#{maxPossibleWin, mode = IN, jdbcType=DECIMAL}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void createDraw(CreateDrawRequest request);

    @Data
    class GetDrawEnrollmentsRequest {
        private Long drawId;
    }

    @Data
    class GetDrawRequest {
        private Long drawId;
    }

    @Data
    class GetDrawEnrollmentPlayerRequest {
        private Long drawEnrollmentId;
    }

    @Data
    class CreateDrawRequest {
        private String name;
        private Date drawDate;
        private int enrollmentCapacity;
        private BigDecimal enrollmentFee;
        private BigDecimal totalWon;
        private BigDecimal maxPossibleWin;
    }
}
