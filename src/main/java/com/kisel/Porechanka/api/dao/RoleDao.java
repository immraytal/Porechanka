package com.kisel.Porechanka.api.dao;

import com.kisel.Porechanka.model.Role;
import com.kisel.Porechanka.util.MyException;

public interface RoleDao {

    Role findByName(String name) throws MyException;
}
