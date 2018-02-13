package be.thibaulthelsmoortel.lotterymanagement.model;

import be.thibaulthelsmoortel.lotterymanagement.session.UserRoles;
import lombok.Data;

import java.util.List;

/**
 * Object representing any application user.
 *
 * @author Thibault Helsmoortel
 */
@Data
public class User {

    private Long id;

    private String username;
    private String password;

    private String firstName;
    private String lastName;

    private String email;

    private List<UserRoles> webRoles;

    public boolean isAdmin() {
        return webRoles.stream().anyMatch(UserRoles.ADMIN::equals);
    }
}
