package com.kisel.Porechanka.service;

import com.kisel.Porechanka.dao.TestDao;
import com.kisel.Porechanka.model.Advert;
import com.kisel.Porechanka.model.dto.AdvertDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestDao testDao;

    @Autowired
    private ModelMapper modelMapper;

    public AdvertDto getAdeverts() {
        return modelMapper.map(testDao.findById(1L).get(), AdvertDto.class);
    }
}
