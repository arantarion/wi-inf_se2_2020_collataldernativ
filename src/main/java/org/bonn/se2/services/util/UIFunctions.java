package org.bonn.se2.services.util;

import com.vaadin.ui.UI;

public class UIFunctions {

    public static void gotoMain() {
        UI.getCurrent().getNavigator().navigateTo(Configuration.Views.MAIN);
    }

}
