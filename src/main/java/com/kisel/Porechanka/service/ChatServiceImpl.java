package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.dao.ChatDao;
import com.kisel.Porechanka.api.dao.UserDao;
import com.kisel.Porechanka.api.service.ChatService;
import com.kisel.Porechanka.model.Chat;
import com.kisel.Porechanka.model.dto.ChatCreateDto;
import com.kisel.Porechanka.model.dto.ChatDto;
import com.kisel.Porechanka.util.MyException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private static final Logger LOG = Logger.getLogger(ChatServiceImpl.class);

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChatDto> getAllChatsByUserWithoutMessages(long userId) {
        List<ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat : chatDao.getAllChatsByUserWithoutMessages(userId)) {
            chatDtos.add(modelMapper.map(chat, ChatDto.class));
        }
        return chatDtos;
    }

    @Override
    public ChatDto getById(long id, int page, int size, long currentUserId) {
        Chat chat = chatDao.getById(id, page, size);
        if (chat.getUserOne().getId() == currentUserId || chat.getUserTwo().getId() == currentUserId) {
            Collections.reverse(chat.getChatMessages());
            return modelMapper.map(chat, ChatDto.class);
        } else {
            LOG.error("User " + currentUserId + " trying to get foreign chat");
            throw new AccessDeniedException("Wrong chatId, pls check parameters");
        }
    }

    @Override
    public ChatDto save(long currentUserId, ChatCreateDto chatCreateDto) {
        if (Collections.disjoint(getAllChatsByUserWithoutMessages(chatCreateDto.getTargetUserId()), getAllChatsByUserWithoutMessages(currentUserId))) {
            Chat chat = new Chat();
            chat.setUserOne(userDao.getById(currentUserId));
            chat.setUserTwo(userDao.getById(chatCreateDto.getTargetUserId()));
            chat.setChatMessages(new ArrayList<>());
            chatDao.save(chat);
            LOG.info("Saving new chat between user " + chat.getUserOne().getId() + " id and " + chat.getUserTwo().getId() + " id");
            return modelMapper.map(chat, ChatDto.class);
        } else {
            for (Chat chat : chatDao.getAllChatsByUserWithoutMessages(chatCreateDto.getTargetUserId())) {
                if (chat.getUserOne().getId() == currentUserId || chat.getUserTwo().getId() == currentUserId) {
                    return modelMapper.map(chat, ChatDto.class);
                }
            }
        }
        LOG.error("Unexpected exception, can't find existing chat, check this");
        throw new MyException("Unexpected exception, please call to the support");
    }

    @Override
    public ChatDto getChatByBothUsers(long userOneId, long userTwoId) {
        Chat chat = chatDao.getChatByBothUsers(userOneId, userTwoId);
        Collections.reverse(chat.getChatMessages());
        return modelMapper.map(chat, ChatDto.class);
    }
}
