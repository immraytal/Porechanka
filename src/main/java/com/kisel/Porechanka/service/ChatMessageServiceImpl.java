package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.dao.ChatDao;
import com.kisel.Porechanka.api.dao.ChatMessageDao;
import com.kisel.Porechanka.api.dao.UserDao;
import com.kisel.Porechanka.api.service.ChatMessageService;
import com.kisel.Porechanka.model.Chat;
import com.kisel.Porechanka.model.ChatMessage;
import com.kisel.Porechanka.model.dto.ChatMessageCreateDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private static final Logger LOG = Logger.getLogger(ChatMessageServiceImpl.class);
    @Autowired
    private ChatMessageDao chatMessageDao;

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void save(ChatMessageCreateDto chatMessageDto, long chatId, long userId) {
        Chat chat = chatDao.getById(chatId);
        if (chat.getUserOne().getId() == userId || chat.getUserTwo().getId() == userId) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setCreatedDate(LocalDate.now());
            chatMessage.setCreatedTime(LocalTime.now());
            chatMessage.setChat(chat);
            chatMessage.setTextMsg(chatMessageDto.getTextMsg());
            chatMessage.setUser(userDao.getById(userId));
            chatMessageDao.save(chatMessage);
            chatDao.update(chat);
            LOG.info("Saving message with " + chatMessage.getId() + " id");
        } else {
            LOG.error("User " + userId + " trying to get foreign chat");
            throw new AccessDeniedException("Wrong chatId, pls check parameters");
        }
    }
}
