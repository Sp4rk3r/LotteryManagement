package be.thibaulthelsmoortel.lotterymanagement.session;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum with supported user roles. A user role defines a user's privileges.
 *
 * @author Thibault Helsmoortel
 */
public enum UserRoles {

    ADMIN("ADMIN"), USER("USER");

    private final String roleString;

    UserRoles(final String roleString) {
        this.roleString = roleString;
    }

    public static String[] getAllNonAdminRoles() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < UserRoles.values().length; i++) {
            if (UserRoles.values()[i] != ADMIN) {
                result.add(UserRoles.values()[i].name());
            }
        }

        return result.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return roleString;
    }
}
