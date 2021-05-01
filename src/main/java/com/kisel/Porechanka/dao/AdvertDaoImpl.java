package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.AdvertDao;
import com.kisel.Porechanka.model.Advert;
import com.kisel.Porechanka.model.AdvertStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdvertDaoImpl extends AbstractDao<Advert> implements AdvertDao {

    public AdvertDaoImpl() {
        super(Advert.class);
    }

    @Override
    public List<Advert> getAllByUserId(long id, int page, int size) {
        return entityManager
                .createQuery("from Advert as a " +
                        "join fetch a.category " +
                        "where a.seller.id = :id " +
                        "and advert_status = '" + AdvertStatus.OPENED + "' " +
                        "or advert_status = '" + AdvertStatus.CLOSED + "' " +
                        "order by is_paid desc", Advert.class)
                .setParameter("id", id)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Advert> getAll(Long categoryId, int page, int size, boolean ascending, String sortType, String search) {
        return entityManager
                .createQuery("from Advert as a " +
                        "join fetch a.category " +
                        "where advert_status=:status" +
                        getCategory(categoryId) +
                        getSerach(search) +
                        " order by a.isPaid desc" +
                        getSort(sortType, ascending), Advert.class)
                .setParameter("status", AdvertStatus.OPENED.toString())
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Advert getById(long id) {
        return entityManager
                .createQuery("from Advert as a " +
                        "join fetch a.category " +
                        "where a.id = :id", Advert.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    private String getCategory(Long categoryId) {
        if (categoryId != 0L) {
            return " and category_id= " + categoryId;
        } else
            return " ";
    }


    private String getSort(String sortType, boolean ascending) {
        if (!sortType.isEmpty() && !sortType.isBlank()) {
            return ", " + sortType + (ascending ? " asc " : " desc ");
        } else
            return " , a.seller.rating desc";
    }

    private String getSerach(String search) {
        if (!search.isEmpty() && !search.isBlank()) {
            return " and title like '%" + search + "%' or body like '%" + search + "%' ";
        } else
            return " ";
    }
}