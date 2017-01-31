package com.tinder.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.tinder.model.webservice.data.Photos;
import com.tinder.model.webservice.data.ProcessedFiles;
import com.tinder.model.webservice.data.Results;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;

public class RecommendationsService {
    private UI userInterface;
    private String userToken;
    private TinderAPI tinderAPI;

    public RecommendationsService(String userToken, UI userInterface, TinderAPI tinderAPI) {
        this.userToken = userToken;
        this.userInterface = userInterface;
        this.tinderAPI = tinderAPI;
    }

    public Layout showRecommendations(List<Results> resultList) {
        VerticalLayout recsLayout = new VerticalLayout();
        for (int i = 0; i < resultList.size(); i++) {
            VerticalLayout fullUserLayout = new VerticalLayout();
            VerticalLayout userInfoLayout = new VerticalLayout();
            userInfoLayout.setMargin(new MarginInfo(true, true, false, true));

            addUserInfo(resultList.get(i), userInfoLayout);
            addLikePassButtons(resultList.get(i), userInfoLayout, recsLayout, i);
            fullUserLayout.addComponent(userInfoLayout);
            addUserPictures(resultList.get(i), fullUserLayout);

            recsLayout.addComponent(fullUserLayout);
        }

        return recsLayout;
    }

    void addUserInfo(Results result, VerticalLayout userInfoLayout) {
        Label nameLabel = new Label("Name : " + result.getName());
        Label distanceLabel = new Label("Distance : " + result.getDistance());
        Label birthDateLabel = new Label("Birth date : " + result.getBirth_date().substring(0, 4));

        // parse the date in Tinder's format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(result.getLastConnection(), formatter);
        // parse the date to be human readable
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
        String lastConnection = dateTime.format(format);
        Label lastConnectionLabel = new Label("Last connection : " + lastConnection);

        userInfoLayout.addComponents(nameLabel, distanceLabel, birthDateLabel, lastConnectionLabel);

        String teaserType = result.getTeaser().getType();
        if (teaserType != null && (teaserType.contains("job") || (teaserType.contains("position")))) {
            Label jobLabel = new Label("Job : " + result.getTeaser().getString());
            userInfoLayout.addComponent(jobLabel);
        }

        if (teaserType != null && teaserType.contains("school")) {
            Label schoolLabel = new Label("School : " + result.getTeaser().getString());
            userInfoLayout.addComponent(schoolLabel);
        }

        if (!result.getBio().isEmpty()) {
            Label bioLabel = new Label("Bio: " + result.getBio());
            userInfoLayout.addComponent(bioLabel);
        }
    }

    void addLikePassButtons(Results result, VerticalLayout userInfoLayout, VerticalLayout fullUserLayout, int index) {
        HorizontalLayout likePassButtonsLayout = new HorizontalLayout();

        Button superlikeButton = new Button("Superlike");
        superlikeButton.setStyleName(ValoTheme.BUTTON_DANGER);

        superlikeButton.addClickListener(e -> {
            int superlikeType = tinderAPI.sendSuperlike(result.getId(), userToken);

            Notification notif = new Notification("", "", Notification.Type.HUMANIZED_MESSAGE);
            if (superlikeType != -1) {
                fullUserLayout.removeComponent(superlikeButton.getParent().getParent().getParent());
                showNotification(superlikeType == 1);
            } else {
                notif.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
                notif.setDescription("Superlikes exceeded, wait a little while...");
            }

            notif.setDelayMsec(2000);
            notif.show(Page.getCurrent());
        });

        Button likeButton = new Button("Like");
        likeButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        likeButton.addClickListener(e -> {
            boolean isMatch = tinderAPI.sendLike(result.getId(), userToken);
            fullUserLayout.removeComponent(likeButton.getParent().getParent().getParent());
            showNotification(isMatch);
        });

        Button passButton = new Button("Pass");
        passButton.addClickListener(e -> {
            tinderAPI.sendPass(result.getId(), userToken);
            fullUserLayout.removeComponent(passButton.getParent().getParent().getParent());
        });

        likePassButtonsLayout.addComponents(superlikeButton, likeButton, passButton);
        userInfoLayout.addComponent(likePassButtonsLayout);
    }

    void addUserPictures(Results result, VerticalLayout fullUserLayout) {
        HorizontalLayout userPicsLayout = new HorizontalLayout();
        for (Photos photo : result.getPhotos()) {
            for (ProcessedFiles file : photo.getProcessedFiles()) {
                if (file.getHeight() == 172) {
                    Image profileImage = new Image();
                    profileImage.setSource(new ExternalResource(file.getUrl()));
                    profileImage.setWidth("172px");
                    profileImage.setHeight("172px");
                    profileImage.setStyleName(BaseTheme.BUTTON_LINK);
                    final String bigPic = file.getUrl().replace("172x172_", "");
                    profileImage.addClickListener(e -> {
                        userInterface.getPage().open(bigPic, "_blank");
                    });

                    userPicsLayout.setMargin(true);
                    userPicsLayout.addComponent(profileImage);
                }
            }
        }
        fullUserLayout.addComponent(userPicsLayout);
    }

    private void showNotification(boolean isMatch) {
        Notification notif = new Notification("", "", Notification.Type.HUMANIZED_MESSAGE);
        if (isMatch) {
            notif.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
            notif.setDescription("This is a new match!");
        } else {
            notif.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
            notif.setDescription("Not yet a match");
        }

        notif.setDelayMsec(2000);
        notif.show(Page.getCurrent());
    }

}