package com.kisel.Porechanka.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "first_user_id")
    private UserModel UserOne;

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "second_user_id")
    private UserModel UserTwo;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "chat"
    )
    private List<ChatMessage> chatMessages;

    public UserModel getUserOne() {
        return UserOne;
    }

    public void setUserOne(UserModel userOne) {
        UserOne = userOne;
    }

    public UserModel getUserTwo() {
        return UserTwo;
    }

    public void setUserTwo(UserModel userTwo) {
        UserTwo = userTwo;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", UserOne=" + UserOne +
                ", UserTwo=" + UserTwo +
                ", chatMessages=" + chatMessages +
                '}';
    }
}
