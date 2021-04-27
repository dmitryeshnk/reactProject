package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.model.Gender;

import java.util.List;

public interface GenderDao {
    void insert(Gender gender);

    List<Gender> findAll();

    void update(Gender gender);

    void delete(Gender gender);

    Gender findGenderById(Long id);
}
