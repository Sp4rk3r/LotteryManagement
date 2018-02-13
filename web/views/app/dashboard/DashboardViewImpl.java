package be.thibaulthelsmoortel.lotterymanagement.web.views.app.dashboard;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource;
import be.thibaulthelsmoortel.lotterymanagement.web.AppUI;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.dashboard.DashboardPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.AppPage;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Actual dashboard view.
 *
 * @author Thibault Helsmoortel
 */
@SpringView(name = DashboardViewImpl.VIEW_NAME, ui = AppUI.class)
public class DashboardViewImpl extends AppPage implements DashboardView, View {

    public static final String VIEW_NAME = "dashboard";

    private final transient List<DashboardViewListener> listeners = new ArrayList<>();

    private final transient DashboardPresenter presenter;

    @Autowired
    public DashboardViewImpl(DashboardPresenter presenter, AppDelegate appDelegate) {
        super(appDelegate);
        this.presenter = presenter;

        setTitle(AppMessageSource.getMessage("dashboard.title"));

        // TODO: 29/09/2017 Remove dummy label
        addComponentsAndExpand(new Label("Welcome to the dashboard!"));
    }

    @PostConstruct
    protected void initPresenter() {
        presenter.init(this);
    }

    @Override
    public void addListener(DashboardViewListener listener) {
        listeners.add(listener);
    }
}
