package be.thibaulthelsmoortel.lotterymanagement.web.components.draws;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource;
import be.thibaulthelsmoortel.lotterymanagement.web.components.common.DecimalField;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.app.draw.DrawOverviewPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import be.thibaulthelsmoortel.lotterymanagement.web.views.utils.validation.No;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.IntegerField;

/**
 * Window used to create instances of {@link be.thibaulthelsmoortel.lotterymanagement.model.Draw}.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class CreateDrawWindow extends Window {

    private VerticalLayout vlContent;
    private TextField tfName;
    private DateField dfDrawDate;
    private IntegerField ifCapacity;
    private DecimalField dfEnrollFee;
    private DecimalField dfMaxWin;
    private Button btnCancel;
    private Button btnSave;

    /**
     * Validation constants.
     */
    private static final int MINIMUM_NAME_LENGTH = 3;
    private static final int MAXIMUM_NAME_LENGTH = 75;
    private static final int MINIMUM_CAPACITY = 1;

    /**
     * Binders for validation.
     */
    private Binder<String> nameBinder;
    private Binder<LocalDate> dateBinder;
    private Binder<Integer> capacityBinder;
    private Binder<BigDecimal> feeBinder;
    private Binder<BigDecimal> maxWinBinder;

    @Autowired
    public CreateDrawWindow(DrawOverviewPresenter presenter) {
        super(getMessage("drawOverview.createDraw"));

        createLayout();
        addValidation();
        addListeners(presenter);

        setModal(true);
        center();
    }

    private void addValidation() {
        nameBinder = new Binder<>();
        nameBinder.forField(tfName)
                .withValidator(new StringLengthValidator(
                        getMessage("draw.add.validation.nameRange", String.valueOf(MINIMUM_NAME_LENGTH), String.valueOf(
                                MAXIMUM_NAME_LENGTH)), MINIMUM_NAME_LENGTH, MAXIMUM_NAME_LENGTH))
                .bind(No.getter(), No.setter());
        dateBinder = new Binder<>();
        dateBinder.forField(dfDrawDate)
                .withValidator(new AbstractValidator<LocalDate>("") {
                    @Override
                    public ValidationResult apply(LocalDate value, ValueContext context) {
                        return value != null ? ValidationResult.ok()
                                : ValidationResult.error(AppMessageSource.getMessage("draw.add.validation.dateEmpty"));
                    }
                })
                .bind(No.getter(), No.setter());
        capacityBinder = new Binder<>();
        capacityBinder.forField(ifCapacity)
                .withValidator(new AbstractValidator<Integer>("") {
                    @Override
                    public ValidationResult apply(Integer value, ValueContext context) {
                        return (value != null && value >= MINIMUM_CAPACITY) ? ValidationResult.ok() : ValidationResult
                                .error(AppMessageSource.getMessage("draw.validation.capacityRange", MINIMUM_CAPACITY));
                    }
                })
                .bind(No.getter(), No.setter());
        feeBinder = new Binder<>();
        feeBinder.forField(dfEnrollFee)
                .withValidator(new AbstractValidator<String>("") {
                    @Override
                    public ValidationResult apply(String value, ValueContext context) {
                        try {
                            dfEnrollFee.verifyNumeric(value);
                            return ValidationResult.ok();
                        } catch (NumberFormatException e) {
                            return ValidationResult
                                    .error(AppMessageSource.getMessage("draw.validation.enrollFee.notNumeric"));
                        }
                    }
                })
                .bind(No.getter(), No.setter());
        maxWinBinder = new Binder<>();
        maxWinBinder.forField(dfMaxWin)
                .withValidator(new AbstractValidator<String>("") {
                    @Override
                    public ValidationResult apply(String value, ValueContext context) {
                        try {
                            dfMaxWin.verifyNumeric(value);
                            return ValidationResult.ok();
                        } catch (NumberFormatException e) {
                            return ValidationResult
                                    .error(AppMessageSource.getMessage("draw.validation.maxWin.notNumeric"));
                        }
                    }
                })
                .bind(No.getter(), No.setter());
    }

    private void addListeners(DrawOverviewPresenter presenter) {
        btnCancel.addClickListener(event -> {
            close();
        });
        btnSave.addClickListener(event -> {
            if (bindersAreValid()) {
                presenter.createDraw(tfName.getValue(), dfDrawDate.getValue(), ifCapacity.getValue(),
                        new BigDecimal(dfEnrollFee.getValue()), new BigDecimal(dfMaxWin.getValue()));
                close();
                clearFields();
            } else {
                validateBinders();
            }
        });
        capacityBinder.addStatusChangeListener(event -> {
            if (event.hasValidationErrors()) {
                ifCapacity.addStyleName(LMTheme.INVALID_FIELD);
            } else {
                ifCapacity.removeStyleName(LMTheme.INVALID_FIELD);
            }
        });
    }

    private void clearFields() {
        tfName.clear();
        dfDrawDate.clear();
        ifCapacity.clear();
        dfEnrollFee.clear();
        dfMaxWin.clear();

        addValidation(); // Reset bindings to remove validation errors
    }

    private void validateBinders() {
        nameBinder.validate();
        dateBinder.validate();
        BinderValidationStatus<Integer> capacityStatus = capacityBinder.validate();
        feeBinder.validate();
        maxWinBinder.validate();
    }

    private boolean bindersAreValid() {
        return nameBinder.isValid() && dateBinder.isValid() && capacityBinder.isValid() && feeBinder.isValid()
                && maxWinBinder.isValid();
    }

    private void createLayout() {
        this.vlContent = new VerticalLayout();

        this.tfName = new TextField(getMessage("draw.name"));
        this.dfDrawDate = new DateField(getMessage("draw.drawDate"));
        this.ifCapacity = new IntegerField(getMessage("draw.capacity"));
        this.dfEnrollFee = new DecimalField(getMessage("draw.enrollmentFee"));
        this.dfMaxWin = new DecimalField(getMessage("draw.maxWin"));
        this.btnCancel = new Button(getMessage("general.cancel"));
        this.btnSave = new Button(getMessage("general.save"));
        btnSave.addStyleName(LMTheme.BUTTON_FRIENDLY);

        vlContent.addComponents(tfName, dfDrawDate, ifCapacity, dfEnrollFee, dfMaxWin,
                new HorizontalLayout(btnCancel, btnSave));

        setContent(vlContent);
    }

}
