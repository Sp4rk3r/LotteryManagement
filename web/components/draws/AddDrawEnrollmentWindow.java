package be.thibaulthelsmoortel.lotterymanagement.web.components.draws;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import be.thibaulthelsmoortel.lotterymanagement.model.Player;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMErrorMessage;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMSuccessMessage;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMWarningMessage;
import be.thibaulthelsmoortel.lotterymanagement.web.components.player.PlayerGrid;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.drawenrollments.DrawEnrollmentsPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.player.AutoCompletePlayerPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import com.google.common.collect.Lists;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addons.autocomplete.AutocompleteExtension;

/**
 * Window used to add instances of {@link be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment} to a {@link
 * be.thibaulthelsmoortel.lotterymanagement.model.Draw}.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class AddDrawEnrollmentWindow extends Window {

    private VerticalLayout vlContent;
    private TextField tfSearchPlayer;
    private List<Player> allPlayers;
    private List<Player> selectedPlayers;
    private Draw draw; // The expected data object on attach event
    private PlayerGrid playerGrid;
    private Button btnCancel;
    private Button btnDeletePlayer;
    private Button btnSave;
    private CheckBox cbFeePaid;

    @Autowired
    public AddDrawEnrollmentWindow(AutoCompletePlayerPresenter playerPresenter,
            DrawEnrollmentsPresenter drawEnrollmentsPresenter) {
        super(getMessage("drawEnrollment.add.title"));

        this.allPlayers = Lists.newArrayList();
        this.selectedPlayers = Lists.newArrayList();

        addWindowListeners(playerPresenter);

        createLayout(drawEnrollmentsPresenter);

        setModal(true);
        center();
    }

    private void addWindowListeners(AutoCompletePlayerPresenter presenter) {
        addAttachListener(event -> {
            this.draw = (Draw) getData();

            tryFetchAllPlayers(presenter); // Update all players for autocomplete
            selectedPlayers.clear();
        });
        addDetachListener(event -> {
            selectedPlayers.clear();
        });
    }

    private void tryFetchAllPlayers(AutoCompletePlayerPresenter presenter) {
        try {
            allPlayers = presenter.getAllPlayers();
        } catch (WebBaseException e) {
            LMErrorMessage lmErrorMessage = new LMErrorMessage(
                    getMessage("drawEnrollment.add.playersNotFetched.title"),
                    getMessage("drawEnrollment.add.playersNotFetched.content")
            );
            lmErrorMessage.show();
        }
    }

    private void createLayout(DrawEnrollmentsPresenter drawEnrollmentsPresenter) {
        vlContent = new VerticalLayout();

        tfSearchPlayer = new TextField();
        tfSearchPlayer.setPlaceholder(getMessage("drawEnrollment.add.searchPlayer"));

        this.playerGrid = new PlayerGrid(selectedPlayers);

        AutocompleteExtension<Player> playerExtension = new AutocompleteExtension<>(tfSearchPlayer);
        playerExtension.setSuggestionGenerator(this::suggestPlayers);
        playerExtension.addSuggestionSelectListener(suggestionSelectEvent -> {
            if (suggestionSelectEvent.getSelectedItem().isPresent() && !selectedPlayers
                    .contains(suggestionSelectEvent.getSelectedItem().get())) {
                tfSearchPlayer.clear(); // Empty the search field

                // Update the selected players
                selectedPlayers.add(suggestionSelectEvent.getSelectedItem().get());
                playerGrid.setItems(selectedPlayers);
            }
        });

        this.btnCancel = new Button(getMessage("general.cancel"));
        this.btnDeletePlayer = new Button(getMessage("general.delete"));
        btnDeletePlayer.addStyleName(LMTheme.BUTTON_DANGER);
        btnDeletePlayer.setEnabled(false);
        this.btnSave = new Button(getMessage("general.save"));
        btnSave.addStyleName(LMTheme.BUTTON_FRIENDLY);
        this.cbFeePaid = new CheckBox(getMessage("drawEnrollment.add.feePaid"));

        addListeners(drawEnrollmentsPresenter);

        vlContent.addComponents(tfSearchPlayer, playerGrid, cbFeePaid,
                new HorizontalLayout(btnCancel, btnDeletePlayer, btnSave));

        setContent(vlContent);
    }

    private void addListeners(DrawEnrollmentsPresenter drawEnrollmentsPresenter) {
        playerGrid.addSelectionListener(event -> {
            btnDeletePlayer.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems()));
        });
        btnCancel.addClickListener(event -> {
            close();
        });
        btnDeletePlayer.addClickListener(event -> {
            Player selectedPlayer = playerGrid.getSelectedPlayer();
            if (selectedPlayer != null) {
                selectedPlayers.remove(selectedPlayer);
                playerGrid.setItems(selectedPlayers);
            }
        });
        btnSave.addClickListener(event -> {
            if (draw.getEnrollments().size() + selectedPlayers.size() <= draw.getEnrollmentCapacity()) {
                if (!draw.getDrawDate().before(new Date())) {
                    drawEnrollmentsPresenter.createDrawEnrollments(draw, selectedPlayers, cbFeePaid.getValue());
                    close();
                } else {
                    LMWarningMessage lmWarningMessage = new LMWarningMessage(
                            getMessage("drawEnrollment.add.warn.drawClosed.title"),
                            getMessage("drawEnrollment.add.warn.drawClosed.content")
                    );
                    lmWarningMessage.show();
                }

            } else {
                LMWarningMessage lmWarningMessage = new LMWarningMessage(
                        getMessage("drawEnrollment.add.warn.capacityReached.title"),
                        getMessage("drawEnrollment.add.warn.capacityReached.content")
                );
                lmWarningMessage.show();
            }
        });
    }

    private List<Player> suggestPlayers(String query, int cap) {
        return allPlayers.stream()
                .filter(player -> !selectedPlayers.contains(player)) // Removes already selected players
                .filter(player -> StringUtils.containsIgnoreCase(player.getFirstName() + player.getLastName(), query))
                .limit(cap).collect(Collectors.toList());
    }
}
