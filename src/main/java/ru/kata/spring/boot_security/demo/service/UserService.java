package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean addRole(Role role);
    List<Role> getListRoles();
    List<Role> getListByRole(List<String> name);
    void add(User user, List<Role> role);
    List<User> getListUsers();
    void delete(Long id);
    void update(User user, List<Role> role);
    User getById(Long id);
    User getByUsername(String userName);
}
