package be.thibaulthelsmoortel.lotterymanagement.mappers;

import be.thibaulthelsmoortel.lotterymanagement.model.User;
import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for {@link be.thibaulthelsmoortel.lotterymanagement.model.User}.
 *
 * @author Thibault Helsmoortel
 */
@Component
@Mapper
public interface UserMapper {

    @Select(" {CALL `p_get_user_roles`("
            + "#{username, mode = IN, jdbcType=VARCHAR}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    @ResultType(RoleWrapper.class)
    @Results(id = "resMapRoles", value = {@Result(id = true, column = "role", property = "role")})
    List<RoleWrapper> getUserRoles(UserRolesRequest userRolesRequest);

    @Select(" {call `p_get_user_login`("
            + "#{username, mode = IN, jdbcType=VARCHAR}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    @ResultType(User.class)
    @Results(id = "resMapLogin", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "email", property = "email"),
    })
    List<User> authenticate(LoginRequest loginRequest);

    @Select(" {CALL `p_register_user`("
            + "#{username, mode = IN, jdbcType=VARCHAR},"
            + "#{firstName, mode = IN, jdbcType=VARCHAR},"
            + "#{lastName, mode = IN, jdbcType=VARCHAR},"
            + "#{email, mode = IN, jdbcType=VARCHAR},"
            + "#{password, mode = IN, jdbcType=VARCHAR}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registerNewUser(SetNewUserRequest request);

    @Select(" {CALL `p_log_login`("
            + "#{id, mode = IN, jdbcType=BIGINT}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void logLoginData(Long id);

    @Select(" {CALL `p_log_logout`("
            + "#{id, mode = IN, jdbcType=BIGINT}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void logLogoutData(Long id);

    @Select(" {CALL `p_get_user`("
            + "#{username, mode = IN, jdbcType=VARCHAR},"
            + "#{email, mode = IN, jdbcType=VARCHAR}"
            + ")}")
    @Options(statementType = StatementType.CALLABLE)
    User getUser(GetUserRequest userRequest);


    @Data
    class LoginRequest {
        private String username;
    }

    @Data
    class UserRolesRequest {
        private String username;
    }

    @Data
    class RoleWrapper {
        private String role;
    }

    @Data
    class SetNewUserRequest {
        String username;
        String firstName;
        String lastName;
        String email;
        String password;
    }

    @Data
    class GetUserRequest {
        String username;
        String email;
    }
}