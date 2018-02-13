package be.thibaulthelsmoortel.lotterymanagement.web.views.utils.validation;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;

/**
 * Convenience empty getter and setter implementation for better readability while 'binding to nothing' for validation.
 *
 * @author Thibault Helsmoortel
 */
public final class No {

    public static <SOURCE, TARGET> ValueProvider<SOURCE, TARGET> getter() {
        return source -> null;
    }

    public static <BEAN, FIELDVALUE> Setter<BEAN, FIELDVALUE> setter() {
        return (bean, fieldValue) -> {
            // No output
        };
    }
}