package com.kisel.Porechanka.api.service;

import com.kisel.Porechanka.model.dto.AdvertCreatePaymentDto;
import com.kisel.Porechanka.model.dto.AdvertPaymentDto;
import com.kisel.Porechanka.model.dto.PaymentApplyDto;


public interface AdvertPaymentService {

    AdvertPaymentDto getLastPaymnetByAdvertId(long id, long userId);

    AdvertPaymentDto createNewCode(AdvertCreatePaymentDto createPaymentDto);

    AdvertPaymentDto verifyAndAddCode(PaymentApplyDto paymentApplyDto, long userId);
}
