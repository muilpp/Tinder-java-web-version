package com.tinder.view;

import com.tinder.model.RecommendationsService;
import com.tinder.model.TinderAPI;
import com.tinder.model.webservice.data.RecommendationsDTO;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class RecommendationsView extends VerticalLayout implements View {
    private static final long serialVersionUID = 3241054721587420815L;
    private String userToken;
    private UI userInterface;
    private TinderAPI tinderAPI;

    public RecommendationsView(String userToken, UI userInterface, TinderAPI tinderAPI) {
        this.userToken = userToken;
        this.userInterface = userInterface;
        this.tinderAPI = tinderAPI;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        RecommendationsDTO recsDTO = tinderAPI.getRecommendations(userToken);
        RecommendationsService recsService = new RecommendationsService(userToken, userInterface, tinderAPI);
        addComponent(recsService.showRecommendations(recsDTO.getResults()));
    }
}