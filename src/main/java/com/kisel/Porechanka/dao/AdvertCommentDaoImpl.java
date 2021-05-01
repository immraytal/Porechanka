package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.AdvertCommentDao;
import com.kisel.Porechanka.model.AdvertComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdvertCommentDaoImpl extends AbstractDao<AdvertComment> implements AdvertCommentDao {

    public AdvertCommentDaoImpl() {
        super(AdvertComment.class);
    }

    @Override
    public List<AdvertComment> getAllByAdvertId(long id) {
        return entityManager
                .createQuery("from AdvertComment as a " +
                        "join fetch a.user " +
                        "left join fetch a.parentAdvertComment " +
                        "where a.advert.id=:id ", AdvertComment.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public AdvertComment getById(long id) {
        return entityManager
                .createQuery("from AdvertComment as a " +
                        "join fetch a.user  " +
                        "left join fetch a.parentAdvertComment " +
                        "where a.id=:id ", AdvertComment.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<AdvertComment> getAllByParentCommentId(long id) {
        return entityManager
                .createQuery("from AdvertComment as a " +
                        "join fetch a.user " +
                        "left join fetch a.parentAdvertComment " +
                        "where a.parentAdvertComment.id=:id ", AdvertComment.class)
                .setParameter("id", id)
                .getResultList();
    }
}
