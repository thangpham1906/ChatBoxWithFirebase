package com.example.thangpham.testfirebasenhap.custom;

import com.example.thangpham.testfirebasenhap.model.ChatMessage;

import java.util.Comparator;

public class MessageComparator implements Comparator<ChatMessage> {
    @Override
    public int compare(ChatMessage chatMessage, ChatMessage t1) {

        return String.valueOf(chatMessage.time).compareTo(String.valueOf(t1.time));
    }
}
