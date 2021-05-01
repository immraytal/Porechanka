package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.GenericDao;
import com.kisel.Porechanka.util.MyException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AbstractDao<T> implements GenericDao<T> {

    public final Class<T> typeParameterClass;

    @PersistenceContext
    protected EntityManager entityManager;

    protected AbstractDao(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public T getById(long id) throws MyException {
        try {
            T t = entityManager.find(typeParameterClass, id);
            return t;
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    @Override
    public List<T> getAll() throws MyException {
        try {
            return entityManager.createQuery("from " + typeParameterClass.getName(), typeParameterClass)
                    .getResultList();
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    @Override
    public void save(T t) throws MyException {
        try {
            entityManager.persist(t);
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    @Override
    public void update(T t) throws MyException {
        try {
            entityManager.merge(t);
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    @Override
    public void delete(long id) throws MyException {
        try {
            T t = getById(id);
            entityManager.remove(t);
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    @Override
    public List<T> sort(String parametr) throws MyException {
        try {
            return entityManager.createQuery("from " + typeParameterClass.getName() + " as a order by :parametr asc ", typeParameterClass)
                    .setParameter("parametr", parametr)
                    .getResultList();
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
}