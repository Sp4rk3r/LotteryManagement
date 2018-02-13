package be.thibaulthelsmoortel.lotterymanagement.web.views.app.draw;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import be.thibaulthelsmoortel.lotterymanagement.web.AppUI;
import be.thibaulthelsmoortel.lotterymanagement.web.components.draws.DrawGrid;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.draw.DrawOverviewPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.AppPage;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

/**
 * Actual DrawOverview view.
 *
 * @author Thibault Helsmoortel
 * @since 3/10/2017
 */
@SpringView(name = DrawOverviewViewImpl.VIEW_NAME, ui = AppUI.class)
public class DrawOverviewViewImpl extends AppPage implements DrawOverviewView, View {

    public static final String VIEW_NAME = "drawOverview";

    private final transient List<DrawOverviewViewListener> listeners = new ArrayList<>();

    private final transient DrawOverviewPresenter presenter;

    private final DrawGrid drawGrid;

    @Autowired
    public DrawOverviewViewImpl(DrawOverviewPresenter presenter, AppDelegate appDelegate,
            DrawGrid drawGrid) {
        super(appDelegate);
        this.presenter = presenter;
        this.drawGrid = drawGrid;

        presenter.init(this);

        setTitle(getMessage("drawOverview.title"));

        this.drawGrid.setItems(presenter.getAllDraws());
        this.drawGrid.setSizeFull();
        addComponent(this.drawGrid);
    }

    @Override
    public void addListener(DrawOverviewViewListener listener) {
        listeners.add(listener);
    }

    @Override
    public void addDrawToGrid(Draw draw) {
        List<Draw> items = drawGrid.getItems();
        items.add(draw);
        drawGrid.setItems(items);
    }
}
