package com.epam.survival.kit.data.dao;

import com.epam.survival.kit.data.model.SurvivalUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<SurvivalUser, Long> {
    SurvivalUser findById(long id);

    SurvivalUser findByUsernameIgnoreCase(String userName);

    SurvivalUser findByEmailIgnoreCase(String email);
}
