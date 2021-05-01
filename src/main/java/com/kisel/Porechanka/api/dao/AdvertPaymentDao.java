package com.kisel.Porechanka.api.dao;

import com.kisel.Porechanka.model.AdvertPayment;

import java.util.List;

public interface AdvertPaymentDao extends GenericDao<AdvertPayment> {

    List<AdvertPayment> getLastPaymnetsByAdvertId(long id);

    AdvertPayment getPaymentByCode(String code);

}
