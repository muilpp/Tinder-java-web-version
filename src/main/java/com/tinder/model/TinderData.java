package com.tinder.model;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.tinder.model.webservice.data.Message;

public class TinderData {

    private static TinderData tinderData;
    private List<Message> messageList;
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(TinderData.class.getSimpleName());

    private TinderData() {
        messageList = Collections.emptyList();
    }

    public static TinderData getInstance() {
        if (tinderData == null) {
            tinderData = new TinderData();
        }

        return tinderData;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}