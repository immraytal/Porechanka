package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.api.service.AdvertCommentService;
import com.kisel.Porechanka.api.service.AdvertService;
import com.kisel.Porechanka.api.service.UserService;
import com.kisel.Porechanka.controller.config.security.SecurityUtils;
import com.kisel.Porechanka.model.dto.AdvertCommentDto;
import com.kisel.Porechanka.model.dto.AdvertDto;
import com.kisel.Porechanka.model.dto.UserModelDto;
import com.kisel.Porechanka.util.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertCommentService advertCommentService;

    @Autowired
    private AdvertService advertService;

    @Autowired
    private SecurityUtils securityUtils;


    @DeleteMapping("/adverts/{id}")
    public List<AdvertDto> deleteAdvert(@PathVariable long id) {
        advertService.delete(id, securityUtils.getCurrentUser().getId(), true);
        return advertService.getAll(Long.parseLong(DefaultValue.ADVERT_CATEGORY),
                Integer.parseInt(DefaultValue.COUNT_PAGES_OF_ADVERTS),
                Integer.parseInt(DefaultValue.COUNT_ADVERTS_ON_PAGE),
                DefaultValue.NO_ORDER_VARIATION,
                DefaultValue.EMPRY_STRING);
    }

    @DeleteMapping("/comments/{id}")
    public List<AdvertCommentDto> deleteComment(@PathVariable long id) {
        return advertCommentService.deleteComment(id, securityUtils.getCurrentUser().getId(), true);
    }

    @PutMapping("/profile/{id}")
    public UserModelDto makeUserAdmin(@PathVariable long id) {
        return advertService.makeUserAsAdmin(id, securityUtils.getCurrentUser().getId());
    }

    @PostMapping("/profile/{id}")
    public UserModelDto toBanUser(@PathVariable long id) {
        return userService.toBanUser(id, securityUtils.getCurrentUser().getId());
    }

}
