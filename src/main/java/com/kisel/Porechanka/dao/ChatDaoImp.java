package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.ChatDao;
import com.kisel.Porechanka.model.Chat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatDaoImp extends AbstractDao<Chat> implements ChatDao {

    public ChatDaoImp() {
        super(Chat.class);
    }

    public List<Chat> getAllChatsByUserWithoutMessages(long userId) {
        List<Chat> chats = entityManager.createQuery("from Chat as c " +
                "join fetch c.UserOne " +
                "join fetch c.UserTwo " +
                "where (c.UserOne.id = :userId " +
                "or c.UserTwo = :userId)", Chat.class)
                .setParameter("userId", userId)
                .getResultList();
        chats.forEach(chat -> chat.setChatMessages(new ArrayList<>()));
        return chats;
    }

    @Override
    public Chat getById(long id, int page, int size) {
        return entityManager.createQuery("from Chat as c " +
                "join fetch c.UserOne " +
                "join fetch c.UserTwo " +
                "where c.id = :chatId ", Chat.class)
                .setParameter("chatId", id)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getSingleResult();
    }

    public Chat getChatByBothUsers(long userOneId, long userTwoId) {
        return entityManager.createQuery("from Chat as c " +
                "join fetch c.UserOne " +
                "join fetch c.UserTwo " +
                "join fetch c.chatMessages " +
                "where (c.UserOne.id = :userOneId " +
                "and c.UserTwo.id = :userTwoId) " +
                "or " +
                "(c.UserOne.id = :userTwoId " +
                "and c.UserTwo.id = :userOneId) ", Chat.class)
                .setParameter("userOneId", userOneId)
                .setParameter("userTwoId", userTwoId)
                .getSingleResult();
    }
}
