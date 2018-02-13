package be.thibaulthelsmoortel.lotterymanagement.model;

import be.thibaulthelsmoortel.lotterymanagement.i18n.Language;
import lombok.Data;

/**
 * Object containing the cookie policy.
 *
 * Note: no id is used here, as the language code can serve as a primary key.
 *
 * @author Thibault Helsmoortel
 */
@Data
public class CookiePolicy {

    private Language language;
    private String policyHtml;

}
