package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.api.service.AdvertCommentService;
import com.kisel.Porechanka.api.service.AdvertService;
import com.kisel.Porechanka.controller.config.security.SecurityUtils;
import com.kisel.Porechanka.model.dto.AdvertCategoryDto;
import com.kisel.Porechanka.model.dto.AdvertCommentCreateDto;
import com.kisel.Porechanka.model.dto.AdvertCommentDto;
import com.kisel.Porechanka.model.dto.AdvertCreateAndUpdateDto;
import com.kisel.Porechanka.model.dto.AdvertDto;
import com.kisel.Porechanka.util.DefaultValue;
import com.kisel.Porechanka.util.validator.ValidationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/adverts")
public class AdvertController {

    private static final Logger LOG = Logger.getLogger(AdvertController.class);

    @Autowired
    private AdvertService advertService;

    @Autowired
    private AdvertCommentService advertCommentService;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    public List<AdvertDto> getAdverts(@RequestParam(value = "page", defaultValue = DefaultValue.COUNT_PAGES_OF_ADVERTS) int page,
                                      @RequestParam(value = "size", defaultValue = DefaultValue.COUNT_ADVERTS_ON_PAGE) int size,
                                      @RequestParam(value = "order", defaultValue = DefaultValue.NO_ORDER_VARIATION) String order,
                                      @RequestParam(value = "category", defaultValue = DefaultValue.ADVERT_CATEGORY) Long category,
                                      @RequestParam(value = "search", defaultValue = DefaultValue.EMPRY_STRING) String search) {
        return advertService.getAll(category, page, size, order, search);
    }

    @GetMapping("/{id}")
    public AdvertDto getById(@PathVariable long id) {
        return advertService.getById(id);
    }

    @GetMapping("/new")
    public List<AdvertCategoryDto> getForm() {
        return advertService.getAllCategories();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public AdvertDto createNewAdvert(@RequestBody AdvertCreateAndUpdateDto advertCreateAndUpdateDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(result));
        } else {
            LOG.info("Request " + advertCreateAndUpdateDto);
            advertCreateAndUpdateDto.setSellerId(securityUtils.getCurrentUser().getId());
            return advertService.save(advertCreateAndUpdateDto);
        }
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void closeAdvert(@PathVariable long id, @RequestParam(value = "buyerId") long buyerId) {
        advertService.close(id, buyerId, securityUtils.getCurrentUser().getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAdvert(@PathVariable long id) {
        advertService.delete(id, securityUtils.getCurrentUser().getId(), false);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody @Validated AdvertCreateAndUpdateDto advertCreateAndUpdateDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(result));
        } else {
            LOG.info("Request " + advertCreateAndUpdateDto);
            advertCreateAndUpdateDto.setSellerId(securityUtils.getCurrentUser().getId());
            advertService.update(advertCreateAndUpdateDto, id, securityUtils.getCurrentUser().getId());
        }
    }

    @GetMapping("/{id}/comments")
    public List<AdvertCommentDto> getAdvertComments(@PathVariable long id) {
        return advertCommentService.getAllByAdvertId(id);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertCommentDto> postNewComment(@PathVariable long id, @RequestBody @Validated AdvertCommentCreateDto advertCommentCreateDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(ValidationUtils.getValidationErrorsAsString(result));
        } else {
            LOG.info("Request " + advertCommentCreateDto);
            return advertCommentService.createNewComment(advertCommentCreateDto, id, securityUtils.getCurrentUser().getId());
        }
    }

    @PutMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertCommentDto> updateComment(@RequestParam(value = "commentId") long commentId, @RequestParam(value = "text") String text) {
        return advertCommentService.updateComment(commentId, text, securityUtils.getCurrentUser().getId());
    }

    @DeleteMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertCommentDto> deleteComment(@RequestParam(value = "commentId") long commentId) {
        return advertCommentService.deleteComment(commentId, securityUtils.getCurrentUser().getId(), false);
    }
}
