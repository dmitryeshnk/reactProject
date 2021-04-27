package com.nixsolutions.ppp.service;

import com.nixsolutions.ppp.model.Gender;

import java.util.List;

public interface GenderService {
    void save(Gender gender);

    List<Gender> findAll();

    void update(Gender gender);

    void delete(Gender gender);

    Gender findGenderById(Long id);
}
