package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.dao.AdvertDao;
import com.kisel.Porechanka.api.dao.AdvertPaymentDao;
import com.kisel.Porechanka.api.service.SheduledService;
import com.kisel.Porechanka.model.Advert;
import com.kisel.Porechanka.model.AdvertPayment;
import com.kisel.Porechanka.util.DefaultValue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SheduledServiceImpl implements SheduledService {

    private static final Logger LOG = Logger.getLogger(SheduledServiceImpl.class);

    @Autowired
    private AdvertDao advertDao;

    @Autowired
    private AdvertPaymentDao advertPaymentDao;

    @Override
    @Scheduled(cron = DefaultValue.SHEDULE_CRON)
    public void checkPaid() {
        for (Advert advert : advertDao.getAll()) {
            AdvertPayment paymnetByAdvertId;
            if (advert.isPaid()) {
                List<AdvertPayment> payments = advertPaymentDao.getLastPaymnetsByAdvertId(advert.getId());
                if (payments.isEmpty()) {
                    return;
                } else {
                    paymnetByAdvertId = payments.get(payments.size() - 1);
                }
                if (paymnetByAdvertId.getPaidForDate().isBefore(LocalDate.now())) {
                    advert.setPaid(false);
                    advertDao.update(advert);
                    LOG.info("Canceling paid for " + advert.getId() + " advert");
                }
            }
        }
        LOG.info(" Scheduled check paid complete");
    }
}
