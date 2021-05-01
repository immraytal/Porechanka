package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.ChatMessageDao;
import com.kisel.Porechanka.model.ChatMessage;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageDaoImpl extends AbstractDao<ChatMessage> implements ChatMessageDao {

    public ChatMessageDaoImpl() {
        super(ChatMessage.class);
    }
}
