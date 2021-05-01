package com.kisel.Porechanka.api.service;

import com.kisel.Porechanka.model.dto.ChatMessageCreateDto;

public interface ChatMessageService {
    void save(ChatMessageCreateDto chatMessageDto, long chatId, long userId);
}
