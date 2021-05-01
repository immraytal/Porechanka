package com.kisel.Porechanka.api.service;

import com.kisel.Porechanka.model.dto.AdvertCategoryDto;
import com.kisel.Porechanka.model.dto.AdvertCreateAndUpdateDto;
import com.kisel.Porechanka.model.dto.AdvertDto;
import com.kisel.Porechanka.model.dto.UserModelDto;

import java.util.List;

public interface AdvertService {

    List<AdvertDto> getAllByUserId(long id, int page, int size);

    List<AdvertDto> getAll(long categoryId, int page, int size, String order, String search);

    List<AdvertCategoryDto> getAllCategories();

    AdvertDto getById(long id);

    AdvertDto save(AdvertCreateAndUpdateDto advertCreateAndUpdateDto);

    AdvertDto update(AdvertCreateAndUpdateDto advertCreateAndUpdateDto, long advertId, long userId);

    void close(long advertId, long buyerId, long userId);

    void delete(long advertId, long userId, boolean isAdmin);

    UserModelDto makeUserAsAdmin(long targetId, long currentUserId);

}
