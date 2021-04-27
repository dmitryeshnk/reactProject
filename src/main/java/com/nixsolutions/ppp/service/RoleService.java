package com.nixsolutions.ppp.service;

import com.nixsolutions.ppp.model.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    List<Role> findAll();

    void update(Role data);

    void delete(Role data);

    Role findByName(String name);
}
