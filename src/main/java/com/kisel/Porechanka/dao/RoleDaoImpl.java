package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.RoleDao;
import com.kisel.Porechanka.model.Role;
import com.kisel.Porechanka.util.MyException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Role findByName(String name) throws MyException {
        return entityManager.createQuery("select r from Role as r where r.roleName = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
