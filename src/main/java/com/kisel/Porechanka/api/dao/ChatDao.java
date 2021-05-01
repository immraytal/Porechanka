package com.kisel.Porechanka.api.dao;

import com.kisel.Porechanka.model.Chat;

import java.util.List;

public interface ChatDao extends GenericDao<Chat> {
    List<Chat> getAllChatsByUserWithoutMessages(long userId);

    Chat getById(long id, int page, int size);

    Chat getChatByBothUsers(long userOneId, long userTwoId);
}
