package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Init {

    private UserServiceImpl userService;

    @Autowired
    public Init(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        userService.addRole(role1);
        userService.addRole(role2);

        List<Role> roleAdmin = new ArrayList<>();
        List<Role> roleUser = new ArrayList<>();
        List<Role> allRoles = new ArrayList<>();

        allRoles.add(role1);
        allRoles.add(role2);
        roleAdmin.add(role1);
        roleUser.add(role2);

        User user1 = new User("admin", "admin", 24, "admin@gmail.com", roleAdmin);
        User user2 = new User("user", "user",  12, "user@gmail.com", roleUser);
        User user3 = new User("user1", "user1", 23, "user1@gmail.com", roleUser);
        User user4 = new User("admin1", "admin1", 42, "user2@gmail.com", allRoles);

        userService.add(user1, roleAdmin);
        userService.add(user2, roleUser);
        userService.add(user3, roleUser);
        userService.add(user4, allRoles);
    }
}
