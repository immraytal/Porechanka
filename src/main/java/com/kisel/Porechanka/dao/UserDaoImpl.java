package com.kisel.Porechanka.dao;

import com.kisel.Porechanka.api.dao.UserDao;
import com.kisel.Porechanka.model.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<UserModel> implements UserDao {

    public UserDaoImpl() {
        super(UserModel.class);
    }

    @Override
    public UserModel findByLogin(String login) {
        return entityManager
                .createQuery("from UserModel as u " +
                        "where u.login = :login", UserModel.class)
                .setParameter("login", login)
                .getSingleResult();
    }


    @Override
    public UserModel getById(long id) {
        return entityManager
                .createQuery("from UserModel as u " +
                        "where u.id = :id", UserModel.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public UserModel findByEmail(String email) {
        return entityManager
                .createQuery("from UserModel as u " +
                        "where u.email=:email", UserModel.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public UserModel findByPhoneNumber(String phone) {
        return entityManager
                .createQuery("from UserModel as u " +
                        "where u.phoneNumber=:phone", UserModel.class)
                .setParameter("phone", phone)
                .getSingleResult();
    }
}
