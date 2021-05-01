package com.kisel.Porechanka.api.dao;

import com.kisel.Porechanka.util.MyException;

import java.util.List;

public interface GenericDao<T> {

    T getById(long id) throws MyException;

    List<T> getAll() throws MyException;

    void save(T t) throws MyException;

    void update(T t) throws MyException;

    void delete(long id) throws MyException;

    List<T> sort(String parametr) throws MyException;
}
