package com.nixsolutions.ppp.service;

import com.nixsolutions.ppp.dao.RoleDao;
import com.nixsolutions.ppp.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService{
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public void save(Role role) {
        roleDao.insert(role);
    }

    @Transactional
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Transactional
    public void update(Role role) {
        roleDao.update(role);
    }

    @Transactional
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Transactional
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
