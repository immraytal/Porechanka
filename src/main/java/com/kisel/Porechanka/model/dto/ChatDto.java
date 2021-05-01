package com.kisel.Porechanka.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("chat")
public class ChatDto {

    private Long id;

    private UserModelDto UserOne;

    private UserModelDto UserTwo;

    private List<ChatMessageDto> chatMessages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModelDto getUserOne() {
        return UserOne;
    }

    public void setUserOne(UserModelDto userOne) {
        UserOne = userOne;
    }

    public UserModelDto getUserTwo() {
        return UserTwo;
    }

    public void setUserTwo(UserModelDto userTwo) {
        UserTwo = userTwo;
    }

    public List<ChatMessageDto> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessageDto> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public String toString() {
        return "ChatDto{" +
                "id=" + id +
                ", UserOne=" + UserOne +
                ", UserTwo=" + UserTwo +
                ", chatMessages=" + chatMessages +
                '}';
    }
}
