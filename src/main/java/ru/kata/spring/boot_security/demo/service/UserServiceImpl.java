package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RoleDaoImpl roleDao;
    private final UserDaoImpl userDao;

    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Autowired
    public UserServiceImpl(RoleDaoImpl roleDao, UserDaoImpl userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public boolean addRole(Role role) {
        Role userPrimary = roleDao.getByName(role.getRole());
        if(userPrimary != null) {return false;}
        roleDao.add(role);
        return true;
    }

    @Override
    public List<Role> getListRoles() { return roleDao.getListRoles(); }

    @Override
    public List<Role> getListByRole(List<String> name) {
        return roleDao.getListByName(name);
    }

    @Override
    @Transactional
    public void add(User user, List<Role> role) {
        user.setRoles(role);
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        userDao.add(user);
    }

    @Override
    public List<User> getListUsers() {
        return userDao.getListUsers();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void update(User user, List<Role> role) {
        String password = getById(user.getId()).getPassword();
        if (user.getPassword().isEmpty()){
            user.setPassword(password);
        } else {
            user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        }
        user.setRoles(role);
        userDao.update(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User getByUsername(String email) {
        return userDao.getByName(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userPrimary = getByUsername(email);
        if (userPrimary == null) {
            throw new UsernameNotFoundException(email + " not found");
        }
        UserDetails user = new org.springframework.security.core.userdetails.User(userPrimary.getUsername(), userPrimary.getPassword(), ath(userPrimary.getRoles()));
        return userPrimary;
    }

    private Collection<? extends GrantedAuthority> ath(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toList());
    }
}