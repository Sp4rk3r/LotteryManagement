package be.thibaulthelsmoortel.lotterymanagement.web.presenters.home;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.web.views.home.HomeView;
import be.thibaulthelsmoortel.lotterymanagement.web.views.home.HomeView.HomeViewListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Presenter for {@link HomeView}.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class HomePresenter implements HomeViewListener {

    private final AppDelegate appDelegate;
    private HomeView view;

    @Autowired
    public HomePresenter(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
    }

    public void init(HomeView view) {
        if (this.view == null) {
            this.view = view;
        }

        view.addListener(this);
    }

}
