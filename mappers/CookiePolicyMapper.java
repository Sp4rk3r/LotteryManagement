package be.thibaulthelsmoortel.lotterymanagement.mappers;

import be.thibaulthelsmoortel.lotterymanagement.model.CookiePolicy;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

/**
 * Mapper for {@link CookiePolicy}.
 *
 * @author Thibault Helsmoortel
 */
@Component
@Mapper
public interface CookiePolicyMapper {

    @Select(" {CALL `p_get_cookie_policy`("
            + "#{languageCode, mode = IN, jdbcType=VARCHAR}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    CookiePolicy getCookiePolicy(GetCookiePolicyRequest request);

    @Select(" {CALL `p_update_cookie_policy`("
            + "#{languageCode, mode = IN, jdbcType=VARCHAR},"
            + "#{policyHtml, mode = IN, jdbcType=LONGVARCHAR}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void updateCookiePolicy(UpdateCookiePolicyRequest request);

    @Data
    class GetCookiePolicyRequest {
        private String languageCode;
    }

    @Data
    class UpdateCookiePolicyRequest {
        private String languageCode;
        private String policyHtml;
    }

}
