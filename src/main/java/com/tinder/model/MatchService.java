package com.tinder.model;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinder.model.webservice.data.Match;
import com.tinder.model.webservice.data.Message;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Service
public class MatchService {
    @Autowired
    private TinderAPI tinderAPI;

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(MatchesService.class.getSimpleName());

    public Layout showMatch(String userToken, Match match) {
        List<Message> messageList = TinderData.getInstance().getMessageList();

        VerticalLayout matchLayout = new VerticalLayout();
        for (Message message : messageList) {
            Layout messageLayout;
            if (message.getFrom().equalsIgnoreCase(match.getPerson().getId())) {
                messageLayout = createNewMessageLayout(message.getMessage(), Alignment.TOP_LEFT);
            } else {
                messageLayout = createNewMessageLayout(message.getMessage(), Alignment.TOP_RIGHT);
            }

            matchLayout.addComponent(messageLayout);
        }

        TextField newMessageText = new TextField();
        newMessageText.setCaption("Say something");
        newMessageText.setSizeFull();
        VerticalLayout newMessageLayout = new VerticalLayout(newMessageText);
        newMessageLayout.setMargin(new MarginInfo(true, false, true, false));

        Button sendButton = new Button("Send");
        sendButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        sendButton.addClickListener(e -> {
            boolean isMessageSent = tinderAPI.sendMessage(userToken, newMessageText.getValue(), match.getId());
            if (isMessageSent) {
                matchLayout.addComponent(createNewMessageLayout(newMessageText.getValue(), Alignment.TOP_RIGHT),
                        matchLayout.getComponentCount() - 1);
                newMessageText.clear();
            }
        });

        newMessageLayout.addComponent(sendButton);

        matchLayout.setWidth("600px");
        matchLayout.setMargin(new MarginInfo(true, false, false, true));
        matchLayout.addComponent(newMessageLayout);

        matchLayout.setComponentAlignment(newMessageLayout, Alignment.TOP_CENTER);
        VerticalLayout fullLayout = new VerticalLayout(matchLayout);
        fullLayout.setComponentAlignment(matchLayout, Alignment.TOP_CENTER);
        return fullLayout;
    }

    private Layout createNewMessageLayout(String message, Alignment alignment) {
        Label nameLabel = new Label(message);
        nameLabel.setSizeUndefined();
        VerticalLayout messageLayout = new VerticalLayout(nameLabel);
        messageLayout.setComponentAlignment(nameLabel, alignment);
        return messageLayout;
    }
}