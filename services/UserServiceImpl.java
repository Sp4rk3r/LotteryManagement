package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.mappers.UserMapper;
import be.thibaulthelsmoortel.lotterymanagement.mappers.UserMapper.GetUserRequest;
import be.thibaulthelsmoortel.lotterymanagement.mappers.UserMapper.LoginRequest;
import be.thibaulthelsmoortel.lotterymanagement.mappers.UserMapper.RoleWrapper;
import be.thibaulthelsmoortel.lotterymanagement.mappers.UserMapper.SetNewUserRequest;
import be.thibaulthelsmoortel.lotterymanagement.mappers.UserMapper.UserRolesRequest;
import be.thibaulthelsmoortel.lotterymanagement.model.User;
import be.thibaulthelsmoortel.lotterymanagement.session.UserRoles;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for {@link be.thibaulthelsmoortel.lotterymanagement.model.User}.
 *
 * @author Thibault Helsmoortel
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void registerNewUser(String username, String firstName, String lastName, String email, String password) {
        SetNewUserRequest request = new SetNewUserRequest();
        request.setUsername(username);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setPassword(password);
        userMapper.registerNewUser(request);
    }

    @Override
    public User authenticate(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        List<User> users = userMapper.authenticate(loginRequest);
        User user = addRoles(users, username);
        if (user != null) {
            // Perform password check
            if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                return null;
            } else {
                userMapper.logLoginData(user.getId());
            }
        }
        return user;
    }

    @Override
    public void logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userMapper.logLogoutData(user.getId());
    }

    @Override
    public User getUser(String username, String email) {
        GetUserRequest request = new GetUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        return userMapper.getUser(request);
    }

    private User addRoles(List<User> result, String username) {
        if (CollectionUtils.isEmpty(result) || result.size() > 1) {
            return null;
        } else {
            // get all userRoles
            UserRolesRequest userRolesRequest = new UserRolesRequest();
            userRolesRequest.setUsername(username);

            List<UserRoles> userRoles = new ArrayList<>();
            List<RoleWrapper> roles = userMapper.getUserRoles(userRolesRequest);

            if (CollectionUtils.isNotEmpty(roles)) {
                roles.forEach(role -> userRoles.add(UserRoles.valueOf(role.getRole())));
            }
            result.get(0).setWebRoles(userRoles);
            return result.get(0);
        }
    }
}