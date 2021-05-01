package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.AdvertCategoryDao;
import com.kisel.Porechanka.model.AdvertCategory;
import org.springframework.stereotype.Repository;

@Repository
public class AdvertCategoryDaoImpl extends AbstractDao<AdvertCategory> implements AdvertCategoryDao {

    public AdvertCategoryDaoImpl() {
        super(AdvertCategory.class);
    }
}
