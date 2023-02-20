package com.example.KvartiraOnline.repository;

import com.example.KvartiraOnline.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository <User, Long> {

    User findByUsername(String username);

    @Override
    void delete (User user);
}
