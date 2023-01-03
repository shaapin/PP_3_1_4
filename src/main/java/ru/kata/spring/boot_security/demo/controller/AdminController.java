package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getListUsers());
        model.addAttribute("role", roleService.getListRoles());
        return "users";
    }

    @GetMapping("/new")
    public String getAddNewUserPage(@AuthenticationPrincipal User user, Model model) {
        User newUser = new User();
        model.addAttribute("user", user);
        model.addAttribute("newUser", newUser);
        model.addAttribute("role", roleService.getListRoles());
        return "new";
    }

    @PostMapping(value = "/saveUser")
    public String addNewUser(@ModelAttribute("newUser") User user,
                             @RequestParam("listRolesNew") ArrayList<Long> roles) {
        userService.add(user, roleService.getRolesListById(roles));
        return "redirect:/admin";
    }

    @PutMapping(value = "edit/{id}")
    public String updateUserById(@ModelAttribute("user") User user,
                                 @RequestParam("listRoles") ArrayList<Long> roles) {
        userService.update(user, roleService.getRolesListById(roles));
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}