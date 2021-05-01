package com.kisel.Porechanka.controller;

import com.kisel.Porechanka.model.Advert;
import com.kisel.Porechanka.model.dto.AdvertDto;
import com.kisel.Porechanka.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/all")
    @ResponseBody
    public AdvertDto getAdverts() {
        return testService.getAdeverts();
    }

}
