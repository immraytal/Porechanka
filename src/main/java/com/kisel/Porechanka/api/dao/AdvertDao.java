package com.kisel.Porechanka.api.dao;

import com.kisel.Porechanka.model.Advert;

import java.util.List;

public interface AdvertDao extends GenericDao<Advert> {

    List<Advert> getAllByUserId(long id, int page, int size);

    List<Advert> getAll(Long categoryId, int page, int size, boolean ascending, String sortType, String search);
}
