package com.tinder.model;

import static com.tinder.model.Constants.PAGE_MATCHES;
import static com.tinder.model.Constants.PAGE_RECS;

import java.io.File;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@Service
public class MainMenuService {
    private Logger LOGGER = Logger.getLogger(MainMenuService.class.getSimpleName());

    public Layout showMainMenu(Navigator navigator) {
        VerticalLayout mainLayout = new VerticalLayout();

        Image changeUserImage = createImage("ic_account_circle_black_48px.svg");
        Label changeUserLabel = new Label("Change user");
        VerticalLayout changeUserLayout = new VerticalLayout(changeUserLabel, changeUserImage);
        changeUserLayout.setWidth("200px");

        Image recsImage = createImage("ic_supervisor_account_black_48px.svg");
        Label recsLabel = new Label("Recommendations");
        VerticalLayout recsLayout = new VerticalLayout(recsLabel, recsImage);

        recsLayout.addLayoutClickListener(e -> {
            navigator.navigateTo(PAGE_RECS);
        });

        HorizontalLayout firstRowLayout = new HorizontalLayout(changeUserLayout, recsLayout);

        Image matchesImage = createImage("ic_chat_black_48px.svg");
        Label matchesLabel = new Label("Matches");
        VerticalLayout matchesLayout = new VerticalLayout(matchesLabel, matchesImage);
        matchesLayout.setWidth("200px");

        matchesLayout.addLayoutClickListener(e -> {
            navigator.navigateTo(PAGE_MATCHES);
        });

        Image changeLocationImage = createImage("ic_add_location_black_48px.svg");
        Label changeLocationLabel = new Label("Change location");
        VerticalLayout changeLocatioLayout = new VerticalLayout(changeLocationLabel, changeLocationImage);

        HorizontalLayout secondRowLayout = new HorizontalLayout(matchesLayout, changeLocatioLayout);

        Image possibleMatchesImage = createImage("ic_face_black_48px.svg");
        Label possibleMatchesLabel = new Label("Possible matches");
        VerticalLayout possibleMatchesLayout = new VerticalLayout(possibleMatchesLabel, possibleMatchesImage);
        possibleMatchesLayout.setWidth("200px");

        Image blocksImage = createImage("ic_visibility_off_black_48px.svg");
        Label blocksLabel = new Label("Blocks");
        VerticalLayout blocksLayout = new VerticalLayout(blocksLabel, blocksImage);

        HorizontalLayout thirdRowLayout = new HorizontalLayout(possibleMatchesLayout, blocksLayout);

        firstRowLayout.setSpacing(true);
        firstRowLayout.setMargin(true);
        secondRowLayout.setSpacing(true);
        secondRowLayout.setMargin(true);
        thirdRowLayout.setSpacing(true);
        thirdRowLayout.setMargin(true);

        mainLayout.addComponents(firstRowLayout, secondRowLayout, thirdRowLayout);
        mainLayout.setComponentAlignment(firstRowLayout, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(secondRowLayout, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(thirdRowLayout, Alignment.TOP_CENTER);

        return mainLayout;
    }

    private Image createImage(String imageLocation) {
        String imagePath = MainMenuService.class.getClassLoader().getResource("static/" + imageLocation).getPath();
        FileResource resource = new FileResource(new File(imagePath));
        Image image = new Image("", resource);
        image.setWidth("80px");
        image.setHeight("80px");

        return image;
    }
}