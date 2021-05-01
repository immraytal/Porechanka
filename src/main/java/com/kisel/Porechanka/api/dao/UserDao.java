package com.kisel.Porechanka.api.dao;

import com.kisel.Porechanka.model.UserModel;

public interface UserDao extends GenericDao<UserModel> {

    UserModel findByLogin(String login);

    UserModel findByEmail(String email);

    UserModel findByPhoneNumber(String phone);
}
