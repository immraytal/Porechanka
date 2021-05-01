package com.kisel.Porechanka.api.service;

import com.kisel.Porechanka.model.dto.UserModelCreateDto;
import com.kisel.Porechanka.model.dto.UserModelDto;

public interface UserService {

    UserModelDto save(UserModelCreateDto userModelCreateDto);

    UserModelDto update(UserModelCreateDto userModelCreateDto, Long userId);

    UserModelDto getById(long id);

    UserModelDto findByLogin(String login);

    UserModelDto toBanUser(long id, long currentAdminId);

    void generateAndSendResetToken(String email);

    String validateResetToken(String token);

}
