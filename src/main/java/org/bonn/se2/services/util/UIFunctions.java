package org.bonn.se2.services.util;

import com.vaadin.ui.UI;

/**
 *
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Henry Weckermann
 */

public class UIFunctions {

    public static void gotoMain() {
        UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
    }

    public static void gotoLogin() {
        UI.getCurrent().getNavigator().navigateTo(Configuration.Views.LOGIN);
    }

    public static void gotoProfile() {
        UI.getCurrent().getNavigator().navigateTo(Configuration.Views.PROFIL);
    }
}
