package com.kisel.Porechanka.api.service;

import com.kisel.Porechanka.model.dto.ChatCreateDto;
import com.kisel.Porechanka.model.dto.ChatDto;

import java.util.List;

public interface ChatService {
    List<ChatDto> getAllChatsByUserWithoutMessages(long userId);

    ChatDto getById(long id, int page, int size, long currentUserId);

    ChatDto save(long currentUserId, ChatCreateDto chatCreateDto);

    ChatDto getChatByBothUsers(long userOneId, long userTwoId);
}
