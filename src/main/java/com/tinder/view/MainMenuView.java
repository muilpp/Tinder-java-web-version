package com.tinder.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinder.model.MainMenuService;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@Service
public class MainMenuView extends VerticalLayout implements View {
    private static final long serialVersionUID = 3241054721587420815L;
    private Navigator navigator;
    @Autowired
    MainMenuService mainMenu;

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        if (this.getComponentCount() == 0)
            addComponent(mainMenu.showMainMenu(navigator));
    }

}
