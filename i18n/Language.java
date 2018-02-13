package be.thibaulthelsmoortel.lotterymanagement.i18n;

import java.util.Arrays;
import java.util.List;

/**
 * Enum of supported languages by the application.
 *
 * @author Thibault Helsmoortel
 * @since 15 Sep 2017
 */
public enum Language {
    ENGLISH("en"), DUTCH("nl");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public static List<Language> getLanguages() {
        return Arrays.asList(values());
    }

    public String getCode() {
        return code;
    }

    public static Language fromCode(String code) {
        return getLanguages().stream().filter(language -> language.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return AppMessageSource.getMessage("language." + code);
    }
}