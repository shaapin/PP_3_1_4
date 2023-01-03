package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getByName(String email) {
        return entityManager.createQuery("select u from User u join fetch u.roles where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList().stream().findAny().orElse(null);
    }

    @Override
    public  void delete(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public boolean add(User user) {
        entityManager.persist(user);
        return true;
    }

    @Override
    public List<User> getListUsers() {
        return entityManager.createQuery("select s from User s", User.class).getResultList();
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }
}
