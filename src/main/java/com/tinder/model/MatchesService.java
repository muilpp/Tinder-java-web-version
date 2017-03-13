package com.tinder.model;

import static com.tinder.model.Constants.MAX_MATCH_PER_ROW;
import static com.tinder.model.Constants.PAGE_MATCH;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.tinder.model.webservice.data.Match;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@Service
public class MatchesService {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(MatchesService.class.getSimpleName());

    public Layout showMatches(Navigator navigator, List<Match> matchList) {
        HorizontalLayout matchesRowLayout = new HorizontalLayout();
        VerticalLayout allMatchesLayout = new VerticalLayout();

        for (int i = 0; i < matchList.size(); i++) {
            Match match = matchList.get(i);
            VerticalLayout matchLayout = new VerticalLayout();
            Label nameLabel = new Label(match.getPerson().getName() + "(" + match.getMessageList().size() + ")");
            Image image = createImage(match.getPerson().getPhotoList().get(0).getUrl());
            matchLayout.addComponents(nameLabel, image);
            matchLayout.setMargin(new MarginInfo(true, false, false, true));

            matchLayout.addLayoutClickListener(e -> {
                TinderData.getInstance().setMessageList(match.getMessageList());
                navigator.navigateTo(PAGE_MATCH + "/" + match.getId());
            });

            matchesRowLayout.addComponent(matchLayout);

            // when 10 matches are added, add the next 10 below
            if (i != 0 && i % MAX_MATCH_PER_ROW == 0) {
                allMatchesLayout.addComponent(matchesRowLayout);
                matchesRowLayout = new HorizontalLayout();
            }
        }

        // there's still something left to be added
        if (matchList.size() % MAX_MATCH_PER_ROW != 0) {
            allMatchesLayout.addComponent(matchesRowLayout);
        }

        allMatchesLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        return allMatchesLayout;
    }

    private Image createImage(String imageLocation) {
        Image changeUserImage = new Image();
        changeUserImage.setSource(new ExternalResource(imageLocation));
        changeUserImage.setWidth("75px");
        changeUserImage.setHeight("75px");

        return changeUserImage;
    }
}