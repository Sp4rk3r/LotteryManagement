package be.thibaulthelsmoortel.lotterymanagement.web.components.common;

import com.vaadin.ui.TextField;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Input field for decimals only.
 *
 * @author Thibault Helsmoortel
 */
public class DecimalField extends TextField {

    /**
     * Supported decimal separators.
     *
     * @author Thibault Helsmoortel
     */
    public enum Separator {
        DOT('.'), COMMA(',');

        private final char separatorChar;

        Separator(char c) {
            this.separatorChar = c;
        }

        public char getChar() {
            return separatorChar;
        }
    }

    private static final int DEFAULT_MAX_INT = 9;
    private static final int DEFAULT_MAX_FRACTION = 9;

    @Getter
    @Setter
    private Separator separator = Separator.DOT;
    @Getter
    @Setter
    private int maxIntegralSize = DEFAULT_MAX_INT;
    @Getter
    @Setter
    private int maxFractionSize = DEFAULT_MAX_FRACTION;

    private final UUID uuid;

    public DecimalField() {
        this.uuid = UUID.randomUUID();
        addDefaultValueChangeListener();
    }

    public DecimalField(String caption) {
        super(caption);
        this.uuid = UUID.randomUUID();
        addDefaultValueChangeListener();
    }

    public DecimalField(String caption, String value) {
        super(caption, value);
        this.uuid = UUID.randomUUID();
        addDefaultValueChangeListener();
    }

    public DecimalField(ValueChangeListener<String> valueChangeListener) {
        super(valueChangeListener);
        this.uuid = UUID.randomUUID();
        addDefaultValueChangeListener();
    }

    public DecimalField(String caption, ValueChangeListener<String> valueChangeListener) {
        super(caption, valueChangeListener);
        this.uuid = UUID.randomUUID();
        addDefaultValueChangeListener();
    }

    public DecimalField(String caption, String value,
            ValueChangeListener<String> valueChangeListener) {
        super(caption, value, valueChangeListener);
        this.uuid = UUID.randomUUID();
        addDefaultValueChangeListener();
    }

    private void addDefaultValueChangeListener() {
        addValueChangeListener(event -> {
            try {
                verifyNumeric(event.getValue());
                setValue(event.getValue().trim());
            } catch (IllegalArgumentException e) {
                if (StringUtils.isNotEmpty(event.getValue())) {
                    if (StringUtils.isBlank(event.getValue())) {
                        setValue("");
                    } else {
                        setValue(event.getOldValue());
                    }

                }
            }
        });
    }

    public boolean verifyNumeric(String value) throws NumberFormatException {
        String input = separator == Separator.DOT ? value : value.replaceAll(String.valueOf(separator.getChar()), String.valueOf(Separator.DOT.getChar()));
        if (input.contains(String.valueOf(Separator.DOT.getChar()))) {
            int separatorIndex = input.indexOf(Separator.DOT.getChar());
            String integerPart = input.substring(0, separatorIndex);
            String decimalPart = input.substring(separatorIndex + 1);
            if (integerPart.length() > maxIntegralSize) {
                throw new IllegalArgumentException("Integer part too long.");
            }
            if (decimalPart.length() > maxFractionSize) {
                throw new IllegalArgumentException("Fraction size too long.");
            }
        }
        Double.valueOf(input);
        return true;
    }

    /**
     * Equals and hashCode required for when you want to add multiple instances to a layout.
     * For this reason the UUID has been introduced as well.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        DecimalField that = (DecimalField) o;

        if (maxIntegralSize != that.maxIntegralSize) {
            return false;
        }
        if (maxFractionSize != that.maxFractionSize) {
            return false;
        }
        if (separator != that.separator) {
            return false;
        }
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + separator.hashCode();
        result = 31 * result + maxIntegralSize;
        result = 31 * result + maxFractionSize;
        result = 31 * result + uuid.hashCode();
        return result;
    }
}
