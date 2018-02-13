package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.i18n.Language;
import be.thibaulthelsmoortel.lotterymanagement.model.CookiePolicy;

/**
 * Service interface for {@link be.thibaulthelsmoortel.lotterymanagement.model.CookiePolicy}.
 *
 * @author Thibault Helsmoortel
 */
public interface CookiePolicyService {

    CookiePolicy getCookiePolicy(Language language);

    void updateCookiePolicy(CookiePolicy policy);

}