package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.dao.AdvertCommentDao;
import com.kisel.Porechanka.api.dao.AdvertDao;
import com.kisel.Porechanka.api.dao.ChatDao;
import com.kisel.Porechanka.api.dao.ChatMessageDao;
import com.kisel.Porechanka.api.dao.UserDao;
import com.kisel.Porechanka.api.service.AdvertCommentService;
import com.kisel.Porechanka.model.AdvertComment;
import com.kisel.Porechanka.model.Chat;
import com.kisel.Porechanka.model.ChatMessage;
import com.kisel.Porechanka.model.UserModel;
import com.kisel.Porechanka.model.dto.AdvertCommentCreateDto;
import com.kisel.Porechanka.model.dto.AdvertCommentDto;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdvertCommentServiceImpl implements AdvertCommentService {

    private static final Logger LOG = Logger.getLogger(AdvertCommentServiceImpl.class);

    @Autowired
    private AdvertCommentDao advertCommentDao;

    @Autowired
    private AdvertDao advertDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private ChatMessageDao chatMessageDao;

    @Override
    public List<AdvertCommentDto> getAllByAdvertId(long id) {
        List<AdvertCommentDto> advertCommentDtos = new ArrayList<>();
        List<AdvertComment> allByAdvertId = advertCommentDao.getAllByAdvertId(id);
        for (AdvertComment advertComment : allByAdvertId) {
            AdvertCommentDto dto = modelMapper.map(advertComment, AdvertCommentDto.class);
            dto.setParentAdvertCommentId(
                    (advertComment.getParentAdvertComment() == null ? null : advertComment.getParentAdvertComment().getId())
            );
            advertCommentDtos.add(dto);
        }
        return advertCommentDtos;
    }

    @Override
    public List<AdvertCommentDto> createNewComment(AdvertCommentCreateDto advertCommentCreateDto, long advertId, long currentUserId) {
        AdvertComment advertComment = new AdvertComment();
        advertComment.setCreatedDate(LocalDate.now());
        advertComment.setCreatedTime(LocalTime.now());
        advertComment.setAdvert(advertDao.getById(advertId));
        advertComment.setText(advertCommentCreateDto.getText());
        long parentCommentId = advertCommentCreateDto.getParentCommentId();
        if (parentCommentId != 0) {
            advertComment.setParentAdvertComment(advertCommentDao.getById(parentCommentId));
        }
        advertComment.setUser(userDao.getById(currentUserId));
        advertCommentDao.save(advertComment);
        LOG.info("Saving new comment " + advertComment.getId() + " id");
        return getAllByAdvertId(advertId);
    }

    @Override
    public List<AdvertCommentDto> updateComment(long commentId, String text, long userId) {
        AdvertComment advertComment = advertCommentDao.getById(commentId);
        long advertId = advertComment.getAdvert().getId();
        if (advertComment.getUser().getId() == userId) {
            advertComment.setCreatedDate(LocalDate.now());
            advertComment.setCreatedTime(LocalTime.now());
            advertComment.setText(text);
            LOG.info("Updating comment " + commentId + " id");
            advertCommentDao.update(advertComment);
            return getAllByAdvertId(advertId);
        } else {
            LOG.error("User " + userId + " trying to update foreign comment");
            throw new AccessDeniedException("You haven't permissions to update this comment");
        }
    }

    @Override
    public List<AdvertCommentDto> deleteComment(long commentId, long currentUserId, boolean isAdmin) {
        AdvertComment advertComment = advertCommentDao.getById(commentId);
        UserModel author = advertComment.getUser();
        UserModel currentUser = userDao.getById(currentUserId);

        if (isAdmin || advertComment.getUser().getId() == currentUserId) {

            if (isAdmin) {
                sendMessageAboutDeletingComment(author, currentUser, advertComment);
            }

            deleteChildComments(commentId, currentUserId);

            LOG.info("Deleting comment " + commentId + " id");
            advertCommentDao.delete(commentId);
            return getAllByAdvertId(advertComment.getAdvert().getId());
        } else {
            LOG.error("User " + currentUserId + " trying to delete foreign comment");
            throw new AccessDeniedException("You haven't permissions to delete this comment");
        }
    }

    private void deleteChildComments(long commentId, long currentUserId) {
        List<AdvertComment> advertComments = advertCommentDao.getAllByParentCommentId(commentId);
        if (!advertComments.isEmpty()) {
            for (AdvertComment comment : advertComments) {
                deleteComment(comment.getId(), currentUserId, true);
            }
        }
    }

    private void sendMessageAboutDeletingComment(UserModel author, UserModel currentUser, AdvertComment advertComment) {
        Chat chat;
        try {
            chat = chatDao.getChatByBothUsers(author.getId(), currentUser.getId());
        } catch (NoResultException e) {
            LOG.info("Chat not found with userOne " + currentUser.getId() + " id, userTwo " + author.getId() + " id");
            chat = new Chat();
            chat.setUserTwo(currentUser);
            chat.setUserOne(author);
            chat.setChatMessages(new ArrayList<>());
            chatDao.save(chat);
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(currentUser);
        chatMessage.setChat(chat);
        chatMessage.setCreatedDate(LocalDate.now());
        chatMessage.setCreatedTime(LocalTime.now());
        String message = "Good day!\nYour comment #" + advertComment.getId() + " '" + advertComment.getText() + "'  has been deleted, because it or parent comment violates the rules of the service";
        chatMessage.setTextMsg(message);
        chatMessageDao.save(chatMessage);
    }
}
