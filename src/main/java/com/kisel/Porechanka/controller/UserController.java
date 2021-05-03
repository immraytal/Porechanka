package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.api.service.AdvertService;
import com.kisel.Porechanka.api.service.ChatMessageService;
import com.kisel.Porechanka.api.service.ChatService;
import com.kisel.Porechanka.api.service.UserService;
import com.kisel.Porechanka.controller.config.security.SecurityUtils;
import com.kisel.Porechanka.model.dto.AdvertDto;
import com.kisel.Porechanka.model.dto.ChatCreateDto;
import com.kisel.Porechanka.model.dto.ChatDto;
import com.kisel.Porechanka.model.dto.ChatMessageCreateDto;
import com.kisel.Porechanka.model.dto.UserModelCreateDto;
import com.kisel.Porechanka.model.dto.UserModelDto;
import com.kisel.Porechanka.util.DefaultValue;
import com.kisel.Porechanka.util.validator.ValidationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertService advertService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    public UserModelDto getCurrentProfile() {
        return userService.getById(securityUtils.getCurrentUser().getId());
    }

    @GetMapping("/{id}")
    public UserModelDto getProfileById(@PathVariable long id) {
        return userService.getById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody @Validated UserModelCreateDto userModelCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOG.error("Incorrect input data " + ValidationUtils.getValidationErrorsAsString(bindingResult));
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(bindingResult));
        } else {
            userService.update(userModelCreateDto, securityUtils.getCurrentUser().getId());
        }
    }

    @GetMapping("/adverts")
    public List<AdvertDto> getUserAdverts(@RequestParam(value = "page", defaultValue = DefaultValue.COUNT_PAGES_OF_ADVERTS) int page,
                                          @RequestParam(value = "size", defaultValue = DefaultValue.COUNT_ADVERTS_ON_PAGE) int size) {
        return advertService.getAllByUserId(securityUtils.getCurrentUser().getId(), page, size);
    }

    @GetMapping("/{id}/adverts")
    public List<AdvertDto> getUserByIdAdverts(@PathVariable long id,
                                              @RequestParam(value = "page", defaultValue = DefaultValue.COUNT_PAGES_OF_ADVERTS) int page,
                                              @RequestParam(value = "size", defaultValue = DefaultValue.COUNT_ADVERTS_ON_PAGE) int size) {
        return advertService.getAllByUserId(id, page, size);
    }

    @GetMapping("/chats")
    public List<ChatDto> getUserChats() {
        return chatService.getAllChatsByUserWithoutMessages(securityUtils.getCurrentUser().getId());
    }

    @PostMapping("/chats")
    @ResponseStatus(HttpStatus.OK)
    public ChatDto createChat(@RequestBody @Validated ChatCreateDto chatCreateDto, BindingResult result) {
        if (result.hasErrors()) {
            LOG.error("Incorrect input data " + ValidationUtils.getValidationErrorsAsString(result));
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(result));
        } else {
            return chatService.save(securityUtils.getCurrentUser().getId(), chatCreateDto);
        }
    }

    @GetMapping("/chats/{chatId}")
    public ChatDto getChatById(@PathVariable long chatId,
                               @RequestParam(value = "page", defaultValue = DefaultValue.COUNT_PAGES_OF_CHAT) int page,
                               @RequestParam(value = "size", defaultValue = DefaultValue.COUNT_MESSAGES_ON_CHAT_PAGE) int size) {
        return chatService.getById(chatId, page, size, securityUtils.getCurrentUser().getId());
    }

    @PostMapping("/chats/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatDto createChatMessage(@RequestBody @Validated ChatMessageCreateDto chatMessageDto, BindingResult result, @PathVariable long chatId) {
        if (result.hasErrors()) {
            LOG.error("Incorrect input data " + ValidationUtils.getValidationErrorsAsString(result));
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(result));
        } else {
            chatMessageService.save(chatMessageDto, chatId, securityUtils.getCurrentUser().getId());
            return getChatById(chatId,
                    Integer.parseInt(DefaultValue.COUNT_PAGES_OF_CHAT),
                    Integer.parseInt(DefaultValue.COUNT_MESSAGES_ON_CHAT_PAGE));
        }
    }
}
