package be.thibaulthelsmoortel.lotterymanagement.web.views.authentication.login;

import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;

/**
 * Login view definition.
 *
 * @author Thibault Helsmoortel
 */
public interface LoginView extends LMView {

    interface LoginViewListener {

    }

    void addListener(LoginViewListener listener);
}
