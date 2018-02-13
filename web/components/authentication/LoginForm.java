package be.thibaulthelsmoortel.lotterymanagement.web.components.authentication;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.model.User;
import be.thibaulthelsmoortel.lotterymanagement.util.authentication.LoginHandler;
import be.thibaulthelsmoortel.lotterymanagement.web.AppUI;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.dashboard.DashboardViewImpl;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

/**
 * Component containing the necessary items for a user to perform general authentication actions.
 *
 * @author Thibault Helsmoortel
 */
@Log4j2
public class LoginForm extends VerticalLayout {

    private static final int USERNAME_MIN_LENGTH = 5;
    private static final int USERNAME_MAX_LENGTH = 50;
    private static final int PASSWORD_MIN_LENGTH = 5;
    private static final int PASSWORD_MAX_LENGTH = 64;

    private final transient LMView view;
    private final Binder<User> binder;

    private TextField tfUsername;
    private PasswordField pfPassword;
    private Button btnLogin;

    public LoginForm(LoginHandler loginHandler, LMView view) {
        this.view = view;

        setCaption(getMessage("authentication.login"));

        addStyleName(LMTheme.LOGIN_FORM);
        setMargin(false);

        this.binder = new Binder<>();

        initComponents();
        addValidation();
        addListeners(loginHandler);

        addComponents(tfUsername, pfPassword, btnLogin);
    }

    private void addValidation() {
        binder.forField(tfUsername).withValidator(new StringLengthValidator(
                getMessage("authentication.login.validation.usernameLength", USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH),
                USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH)).bind(User::getUsername, User::setUsername);
        binder.forField(pfPassword).withValidator(new StringLengthValidator(
                getMessage("authentication.login.validation.passwordLength", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
                PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)).bind(User::getPassword, User::setPassword);
    }

    private void addListeners(LoginHandler loginHandler) {
        btnLogin.addClickListener((ClickListener) event -> tryPerformLogin(loginHandler));

        addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                tryPerformLogin(loginHandler);
            }
        });
    }

    private void initComponents() {
        this.tfUsername = new TextField(getMessage("authentication.username"));
        this.pfPassword = new PasswordField(getMessage("authentication.password"));
        this.btnLogin = new Button(getMessage("authentication.login"));
    }

    private void tryPerformLogin(LoginHandler loginHandler) {
        if (binder.isValid()) {
            log.info("Trying to login user: '{}'.", getUsernameValue());

            loginHandler.handleLogin(this, () -> {
                        Page page = UI.getCurrent().getPage();
                        page.open(AppUI.UI_NAME + "/#!" + DashboardViewImpl.VIEW_NAME, page.getWindowName(), false);
                    }
            );
        } else {
            view.showError(getMessage("login.error.invalidInput.title"), getMessage("login.error.invalidInput.content"));
        }
    }

    public String getUsernameValue() {
        return tfUsername.getValue();
    }

    public String getPasswordValue() {
        return pfPassword.getValue();
    }

    public void showLoginError(AuthenticationException e) {
        if (e instanceof BadCredentialsException) {
            view.showError("login.error.badCredentials.title", "login.error.badCredentials.content");
        } else if (e instanceof AuthenticationServiceException) {
            view.showError("login.error.loginFailed.title", "login.error.loginFailed.content");
        }
    }
}
