package com.nixsolutions.ppp.service;

import com.nixsolutions.ppp.dao.GenderDao;
import com.nixsolutions.ppp.model.Gender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenderServiceImp implements GenderService{

    private final GenderDao genderDao;

    public GenderServiceImp(GenderDao genderDao) {
        this.genderDao = genderDao;
    }

    @Transactional
    public void save(Gender gender) {
        genderDao.insert(gender);
    }

    @Transactional
    public List<Gender> findAll() {
        return genderDao.findAll();
    }

    @Transactional
    public void update(Gender gender) {
        genderDao.update(gender);
    }

    @Transactional
    public void delete(Gender gender) {
        genderDao.delete(gender);
    }

    @Transactional
    public Gender findGenderById(Long id) {
        return genderDao.findGenderById(id);
    }
}
