package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.dao.AdvertCategoryDao;
import com.kisel.Porechanka.api.dao.AdvertDao;
import com.kisel.Porechanka.api.dao.ChatDao;
import com.kisel.Porechanka.api.dao.ChatMessageDao;
import com.kisel.Porechanka.api.dao.RoleDao;
import com.kisel.Porechanka.api.dao.UserDao;
import com.kisel.Porechanka.api.service.AdvertService;
import com.kisel.Porechanka.model.Advert;
import com.kisel.Porechanka.model.AdvertCategory;
import com.kisel.Porechanka.model.AdvertStatus;
import com.kisel.Porechanka.model.Chat;
import com.kisel.Porechanka.model.ChatMessage;
import com.kisel.Porechanka.model.Role;
import com.kisel.Porechanka.model.UserModel;
import com.kisel.Porechanka.model.dto.AdvertCategoryDto;
import com.kisel.Porechanka.model.dto.AdvertCreateAndUpdateDto;
import com.kisel.Porechanka.model.dto.AdvertDto;
import com.kisel.Porechanka.model.dto.UserModelDto;
import com.kisel.Porechanka.util.DefaultValue;
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
public class AdvertServiceImpl implements AdvertService {

    private static final Logger LOG = Logger.getLogger(AdvertServiceImpl.class);

    @Autowired
    private AdvertDao advertDao;

    @Autowired
    private AdvertCategoryDao advertCategoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ChatMessageDao chatMessageDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AdvertDto getById(long id) {
        Advert advert = advertDao.getById(id);
        return modelMapper.map(advert, AdvertDto.class);
    }

    @Override
    public List<AdvertDto> getAllByUserId(long id, int page, int size) {
        List<AdvertDto> advertDtos = new ArrayList<>();
        for (Advert advert : advertDao.getAllByUserId(id, page, size)) {
            advertDtos.add(modelMapper.map(advert, AdvertDto.class));
        }
        return advertDtos;
    }

    @Override
    public List<AdvertCategoryDto> getAllCategories() {
        List<AdvertCategoryDto> categoryDtos = new ArrayList<>();
        for (AdvertCategory advertCategory : advertCategoryDao.getAll()) {
            categoryDtos.add(modelMapper.map(advertCategory, AdvertCategoryDto.class));
        }
        return categoryDtos;
    }

    @Override
    public List<AdvertDto> getAll(long categoryId, int page, int size, String order, String search) {
        List<AdvertDto> advertDtos = new ArrayList<>();
        for (Advert advert : getAdvertsByCustomSortType(categoryId, page, size, order, search)) {
            advertDtos.add(modelMapper.map(advert, AdvertDto.class));
        }
        return advertDtos;
    }

    private List<Advert> getAdvertsByCustomSortType(long categoryId, int page, int size, String order, String search) {
        switch (order) {
            case DefaultValue.NO_ORDER_VARIATION:
                return advertDao.getAll(categoryId, page, size, false, "", search);
            case "price.asc":
                return advertDao.getAll(categoryId, page, size, true, "price", search);
            case "price.desc":
                return advertDao.getAll(categoryId, page, size, false, "price", search);
            case "date.asc":
                return advertDao.getAll(categoryId, page, size, true, "open_date", search);
            case "date.desc":
                return advertDao.getAll(categoryId, page, size, false, "open_date", search);
            default:
                return advertDao.getAll(categoryId, page, size, false, "", search);
        }
    }

    @Override
    public AdvertDto save(AdvertCreateAndUpdateDto advertCreateAndUpdateDto) {
        Advert advert = new Advert();
        advert.setOpenDate(LocalDate.now());
        advert.setOpenTime(LocalTime.now());
        advert.setSeller(userDao.getById(advertCreateAndUpdateDto.getSellerId()));
        advert.setTitle(advertCreateAndUpdateDto.getTitle());
        advert.setBody(advertCreateAndUpdateDto.getBody());
        advert.setCategory(advertCategoryDao.getById(advertCreateAndUpdateDto.getCategoryId()));
        advert.setPrice(advertCreateAndUpdateDto.getPrice());
        advert.setAdvertStatus(AdvertStatus.OPENED);
        advert.setPhotoUrl(advertCreateAndUpdateDto.getPhotoUrl());
        advertDao.save(advert);
        LOG.info("Saving new advert " + advert.getId() + " id " + advert.getTitle());
        return modelMapper.map(advert, AdvertDto.class);
    }

    @Override
    public AdvertDto update(AdvertCreateAndUpdateDto advertCreateAndUpdateDto, long advertId, long userId) {
        Advert advert = advertDao.getById(advertId);
        if (advert.getSeller().getId() == userId) {
            advert.setOpenDate(LocalDate.now());
            advert.setOpenTime(LocalTime.now());
            advert.setTitle(advertCreateAndUpdateDto.getTitle());
            advert.setSeller(userDao.getById(advertCreateAndUpdateDto.getSellerId()));
            advert.setBody(advertCreateAndUpdateDto.getBody());
            advert.setPrice(advertCreateAndUpdateDto.getPrice());
            advert.setCategory(advertCategoryDao.getById(advertCreateAndUpdateDto.getCategoryId()));
            advert.setAdvertStatus(AdvertStatus.OPENED);
            advert.setPhotoUrl(advertCreateAndUpdateDto.getPhotoUrl());
            advertDao.update(advert);
            LOG.info("Updating new advert " + advert.getId() + " id " + advert.getTitle());
            return modelMapper.map(advert, AdvertDto.class);
        } else {
            LOG.error("User " + userId + " trying to update foreign advert " + advert.getId() + " id");
            throw new AccessDeniedException("You haven't permissions to update this advert, you aren't seller");
        }
    }

    @Override
    public void close(long advertId, long buyerId, long userId) {
        Advert advert = advertDao.getById(advertId);
        if (advert.getSeller().getId() == userId) {
            advert.setCloseDate(LocalDate.now());
            advert.setCloseTime(LocalTime.now());
            advert.setAdvertStatus(AdvertStatus.CLOSED);
            advert.setPaid(false);
            advert.setBuyer(userDao.getById(buyerId));
            UserModel seller = advert.getBuyer();
            seller.setRating(seller.getRating() + 1L);
            LOG.info("Closing advert " + advert.getId() + " id " + advert.getTitle());
            userDao.update(seller);
            advertDao.update(advert);
        } else {
            LOG.error("User " + userId + " trying to close foreign advert " + advert.getId() + " id");
            throw new AccessDeniedException("You haven't permissions to close this advert, you aren't seller");
        }
    }

    @Override
    public void delete(long advertId, long currentUserId, boolean isAdmin) {
        Advert advert = advertDao.getById(advertId);
        UserModel seller = advert.getSeller();
        UserModel currentUser = userDao.getById(currentUserId);

        if (isAdmin || advert.getSeller().getId() == currentUserId) {
            if (isAdmin) {
                sendMessageAboutDeletingAdvert(seller, currentUser, advert);
            }
            advert.setCloseDate(LocalDate.now());
            advert.setCloseTime(LocalTime.now());
            advert.setAdvertStatus(AdvertStatus.DELETED);
            advert.setPaid(false);
            LOG.info("Deleting advert " + advert.getId() + " id " + advert.getTitle());
            advertDao.update(advert);
        } else {
            LOG.error("User " + currentUserId + " trying to delete foreign advert " + advert.getId() + " id");
            throw new AccessDeniedException("You haven't permissions to delete this advert, you aren't seller");
        }
    }

    private void sendMessageAboutDeletingAdvert(UserModel seller, UserModel currentUser, Advert advert) {
        Chat chat;
        try {
            chat = chatDao.getChatByBothUsers(seller.getId(), currentUser.getId());
        } catch (NoResultException e) {
            LOG.info("Chat not found with userOne " + currentUser.getId() + " id, userTwo " + seller.getId() + " id");
            chat = new Chat();
            chat.setUserOne(seller);
            chat.setUserTwo(currentUser);
            chat.setChatMessages(new ArrayList<>());
            chatDao.save(chat);
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(currentUser);
        chatMessage.setChat(chat);
        chatMessage.setCreatedTime(LocalTime.now());
        chatMessage.setCreatedDate(LocalDate.now());
        String message = "Good day!\nYour advert #" + advert.getId() + " " + advert.getTitle() + " has been deleted, because it violates the rules of the service";
        chatMessage.setTextMsg(message);
        chatMessageDao.save(chatMessage);
    }

    @Override
    public UserModelDto makeUserAsAdmin(long targetId, long currentUserId) {
        UserModel userModel = userDao.getById(targetId);
        Role role = roleDao.findByName(DefaultValue.ADMIN_ROLE);
        userModel.setRole(role);
        LOG.info("Admin #" + currentUserId + " make User #" + targetId + " as admin");
        userDao.update(userModel);
        return modelMapper.map(userModel, UserModelDto.class);
    }

}
