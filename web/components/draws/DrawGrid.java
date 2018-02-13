package be.thibaulthelsmoortel.lotterymanagement.web.components.draws;

import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;

import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

/**
 * Grid for displaying {@link be.thibaulthelsmoortel.lotterymanagement.model.Draw} elements.
 *
 * @author Thibault Helsmoortel
 * @since 3/10/2017
 */
@SpringComponent
@ViewScope
public class DrawGrid extends VerticalLayout {

    private final Button btnCreateDraw;
    private final Button btnEnrollPlayer;
    private final Grid<Draw> drawGrid;

    // Required field to make list updates easier
    @Getter
    private List<Draw> items;

    @Autowired
    public DrawGrid(CreateDrawWindow createDrawWindow, AddDrawEnrollmentWindow addDrawEnrollmentWindow) {
        this.btnCreateDraw = new Button(getMessage("drawOverview.createDraw"));
        btnCreateDraw.setIcon(VaadinIcons.PLUS);
        this.btnEnrollPlayer = new Button(getMessage("drawOverview.enrollPlayers"));
        btnEnrollPlayer.setIcon(VaadinIcons.PLUS);

        this.drawGrid = new Grid<>();
        drawGrid.setSelectionMode(SelectionMode.SINGLE);
        drawGrid.addColumn(Draw::getName).setCaption(getMessage("drawOverview.grid.name"));
        drawGrid.addColumn(Draw::getDrawDate).setCaption(getMessage("drawOverview.grid.date"));
        drawGrid.addColumn(Draw::getEnrollmentCapacity).setCaption(getMessage("drawOverview.grid.capacity"));
        drawGrid.addColumn(Draw::getEnrollmentFee).setCaption(getMessage("drawOverview.grid.fee"));
        drawGrid.addColumn(Draw::getMaxPossibleWin).setCaption(getMessage("drawOverview.grid.maxWin"));
        drawGrid.addColumn(Draw::getTotalWon).setCaption(getMessage("drawOverview.grid.totalWon"));
        drawGrid.setSizeFull();

        setMargin(false);
        addComponents(drawGrid, new HorizontalLayout(btnCreateDraw, btnEnrollPlayer));

        btnCreateDraw.addClickListener(event ->{
            UI.getCurrent().addWindow(createDrawWindow);
        });
        btnEnrollPlayer.addClickListener(event -> {
            Draw selected = getSelectedDraw();
            if (selected != null) {
                addDrawEnrollmentWindow.setData(selected); // Attach the selected draw to the window
                UI.getCurrent().addWindow(addDrawEnrollmentWindow);
            }
        });
    }

    public void setItems(List<Draw> draws) {
        this.items = draws;
        drawGrid.setItems(draws);
    }

    private Draw getSelectedDraw() {
        return drawGrid.getSelectedItems().stream().findFirst().orElse(null);
    }
}
