package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.api.service.AdvertService;
import com.kisel.Porechanka.model.dto.AdvertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private AdvertService advertService;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/one")
    @ResponseBody
    public AdvertDto giveOne() {
        return advertService.getById(1);
    }
}
