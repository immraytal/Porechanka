package com.kisel.Porechanka.api.dao;

import com.kisel.Porechanka.model.AdvertComment;

import java.util.List;

public interface AdvertCommentDao extends GenericDao<AdvertComment> {

    List<AdvertComment> getAllByAdvertId(long id);

    List<AdvertComment> getAllByParentCommentId(long id);
}
