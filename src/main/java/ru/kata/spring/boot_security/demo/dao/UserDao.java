package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    User getByName(String username);
    void delete(Long id);
    void update(User user);
    boolean add(User user);
    List<User> getListUsers();
    User getById(Long id);
}
