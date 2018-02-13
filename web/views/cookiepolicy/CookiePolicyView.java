package be.thibaulthelsmoortel.lotterymanagement.web.views.cookiepolicy;

import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView;

/**
 * Cookie policy view definition.
 *
 * @author Thibault Helsmoortel
 */
public interface CookiePolicyView extends LMView {

    interface CookiePolicyViewListener {

    }

    void addListener(CookiePolicyViewListener listener);

}