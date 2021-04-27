package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.model.Role;

import java.util.List;

public interface RoleDao {
    void insert(Role role);

    List<Role> findAll();

    void update(Role data);

    void delete(Role data);

    Role findByName(String name);
}
