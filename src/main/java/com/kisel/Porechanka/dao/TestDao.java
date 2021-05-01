package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.model.Advert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends CrudRepository<Advert, Long>
{
}