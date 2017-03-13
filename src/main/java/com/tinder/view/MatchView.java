package com.tinder.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinder.model.MatchService;
import com.tinder.model.TinderAPI;
import com.tinder.model.webservice.data.MatchDTO;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@Service
public class MatchView extends VerticalLayout implements View {
    private static final long serialVersionUID = 3241054721587420815L;
    private String userToken;

    @Autowired
    private TinderAPI tinderAPI;

    @Autowired
    private MatchService matchService;

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // remove the previous chat messages from the view
        removeAllComponents();
        MatchDTO matchDTO = tinderAPI.getMatchInfo(userToken, event.getParameters());

        if (this.getComponentCount() == 0)
            addComponent(matchService.showMatch(userToken, matchDTO.getMatch()));
    }
}