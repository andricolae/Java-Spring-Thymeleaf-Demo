package com.demo.spring.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepo repo;

    public List<User> listAll() {

        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> res = repo.findById(id);
        if(res.isPresent())
            return res.get();
        throw new UserNotFoundException("Could not find any users with ID: " + id);
    }
}
