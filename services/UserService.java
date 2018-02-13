package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.model.User;

/**
 * Service interface for {@link User}.
 *
 * @author Thibault Helsmoortel
 */
public interface UserService {

    void registerNewUser(String username, String firstName, String lastName, String email, String password);

    User authenticate(String name, String password);

    void logout();

    User getUser(String username, String email);
}
