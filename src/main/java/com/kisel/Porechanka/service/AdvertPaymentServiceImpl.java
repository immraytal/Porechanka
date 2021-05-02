package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.dao.AdvertDao;
import com.kisel.Porechanka.api.dao.AdvertPaymentDao;
import com.kisel.Porechanka.api.service.AdvertPaymentService;
import com.kisel.Porechanka.model.Advert;
import com.kisel.Porechanka.model.AdvertPayment;
import com.kisel.Porechanka.model.dto.AdvertCreatePaymentDto;
import com.kisel.Porechanka.model.dto.AdvertPaymentDto;
import com.kisel.Porechanka.model.dto.PaymentApplyDto;
import com.kisel.Porechanka.util.DefaultValue;
import com.kisel.Porechanka.util.MyException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class AdvertPaymentServiceImpl implements AdvertPaymentService {

    private static final Logger LOG = Logger.getLogger(AdvertPaymentServiceImpl.class);

    @Autowired
    private AdvertPaymentDao advertPaymentDao;

    @Autowired
    private AdvertDao advertDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AdvertPaymentDto getLastPaymnetByAdvertId(long id, long userId) {
        Advert advert = advertDao.getById(id);
        if (advert.getSeller().getId() == userId) {
            List<AdvertPayment> payments = advertPaymentDao.getLastPaymnetsByAdvertId(id);
            if (payments.isEmpty()) {
                LOG.error("Advert " + id + " id haven't any payments");
                throw new MyException("Haven't any payments on this advert");
            } else {
                return modelMapper.map(payments.get(payments.size() - 1), AdvertPaymentDto.class);
            }
        } else {
            LOG.error("User" + userId + " id hasn't access for this advert " + advert.getId() + " id");
            throw new AccessDeniedException("Current user hasn't access for this advert");
        }
    }

    @Override
    public AdvertPaymentDto createNewCode(AdvertCreatePaymentDto createPaymentDto) {
        AdvertPayment advertPayment = new AdvertPayment();
        advertPayment.setPaidForDate(createPaymentDto.getPaidForDate());
        advertPayment.setVerifyCode(getRandomCode());
        advertPaymentDao.save(advertPayment);
        LOG.info("Saving new code " + advertPayment.getVerifyCode().substring(0, 5) + "..");
        return modelMapper.map(advertPayment, AdvertPaymentDto.class);
    }

    @Override
    public AdvertPaymentDto verifyAndAddCode(PaymentApplyDto paymentApplyDto, long userId) {
        String code = paymentApplyDto.getCode();
        Advert advert = advertDao.getById(paymentApplyDto.getAdvertId());
        if (advert.getSeller().getId() == userId) {
            AdvertPayment advertPayment = advertPaymentDao.getPaymentByCode(code);
            if (advertPayment.getAdvert() != null) {
                LOG.error("Code " + code.substring(0, 5) + ".. is already used");
                throw new MyException("Code is already activated, pls enter another code");
            }
            advertPayment.setAdvert(advert);
            advert.setPaid(true);
            advertPaymentDao.update(advertPayment);
            advertDao.update(advert);
            LOG.info("Verifing payment code " + code.substring(0, 5) + "..");
            return modelMapper.map(advertPayment, AdvertPaymentDto.class);
        } else {
            LOG.error("User" + userId + " id hasn't access for this advert " + advert.getId() + " id");
            throw new AccessDeniedException("Current user hasn't access for this advert");
        }
    }

    private String getRandomCode() {
        return new Random().ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(DefaultValue.LENGTH_OF_PAYMENT_CODE)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
