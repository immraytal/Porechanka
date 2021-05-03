package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.api.service.AdvertPaymentService;
import com.kisel.Porechanka.api.service.AdvertService;
import com.kisel.Porechanka.controller.config.security.SecurityUtils;
import com.kisel.Porechanka.model.dto.AdvertCreatePaymentDto;
import com.kisel.Porechanka.model.dto.AdvertDto;
import com.kisel.Porechanka.model.dto.AdvertPaymentDto;
import com.kisel.Porechanka.model.dto.PaymentApplyDto;
import com.kisel.Porechanka.util.DefaultValue;
import com.kisel.Porechanka.util.validator.ValidationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.List;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger LOG = Logger.getLogger(PaymentController.class);

    @Autowired
    private AdvertPaymentService advertPaymentService;

    @Autowired
    private AdvertService advertService;

    @Autowired
    private SecurityUtils securityUtils;

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public AdvertPaymentDto createNewCode(@Validated @RequestBody AdvertCreatePaymentDto advertCreatePaymentDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(result));
        } else {
            return advertPaymentService.createNewCode(advertCreatePaymentDto);
        }
    }

    @GetMapping
    public AdvertPaymentDto getByAdvert(@RequestParam(value = "advertId") long advertId) {
        return advertPaymentService.getLastPaymnetByAdvertId(advertId, securityUtils.getCurrentUser().getId());
    }

    @GetMapping("/add")
    public List<AdvertDto> getUserAdverts() {
        return advertService.getAllByUserId(securityUtils.getCurrentUser().getId(), Integer.parseInt(DefaultValue.COUNT_PAGES_OF_ADVERTS), Integer.MAX_VALUE);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public AdvertPaymentDto addAdvertToTop(@RequestBody @Validated PaymentApplyDto paymentApplyDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOG.error("Incorrect input data " + ValidationUtils.getValidationErrorsAsString(bindingResult));
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(bindingResult));
        } else {
            return advertPaymentService.verifyAndAddCode(paymentApplyDto, securityUtils.getCurrentUser().getId());
        }
    }
}
