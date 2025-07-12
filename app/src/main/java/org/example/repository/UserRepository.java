package org.example.repository;

import org.example.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
    // It will automatically write sql query findByUserName from user Table where username = userName;
    public UserInfo findByUserName(String userName);
}
