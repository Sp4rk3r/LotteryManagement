package be.thibaulthelsmoortel.lotterymanagement.web.presenters.authentication.login;

import be.thibaulthelsmoortel.lotterymanagement.web.views.authentication.login.LoginView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import java.io.Serializable;

/**
 * Presenter for {@link LoginView}.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class LoginPresenter implements LoginView.LoginViewListener {

    private LoginView view;

    public void init(LoginView view) {
        if (this.view == null) {
            this.view = view;

            view.addListener(this);
        }
    }
}
