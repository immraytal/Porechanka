package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.AdvertPaymentDao;
import com.kisel.Porechanka.model.AdvertPayment;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class AdvertPaymentDaoImpl extends AbstractDao<AdvertPayment> implements AdvertPaymentDao {


    public AdvertPaymentDaoImpl() {
        super(AdvertPayment.class);
    }

    @Override
    public List<AdvertPayment> getLastPaymnetsByAdvertId(long id) {
        List<AdvertPayment> payments = entityManager
                .createQuery("from AdvertPayment as a " +
                        "where a.advert.id = :id", AdvertPayment.class)
                .setParameter("id", id)
                .getResultList();
        payments.sort(Comparator.comparing(AdvertPayment::getPaidForDate));
        return payments;
    }

    @Override
    public AdvertPayment getPaymentByCode(String code) {
        return entityManager
                .createQuery("from AdvertPayment as a " +
                        "where a.verifyCode=:code", AdvertPayment.class)
                .setParameter("code", code)
                .getSingleResult();
    }
}
