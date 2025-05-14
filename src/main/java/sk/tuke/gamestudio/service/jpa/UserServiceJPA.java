package sk.tuke.gamestudio.service.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserException;
import sk.tuke.gamestudio.service.UserService;

import java.util.List;

@Transactional
public class UserServiceJPA implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void signUp(User user) throws UserException {
        try {
            List<User> existing = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", user.getName())
                    .getResultList();
            if (!existing.isEmpty()) {
                throw new UserException("User already exists");
            }

            user.setPassword(encoder.encode(user.getPassword()));
            entityManager.persist(user);
        } catch (Exception e) {
            throw new UserException("Failed to sign up", e);
        }
    }

    @Override
    public User loginUser(String name, String password) throws UserException {
        try {
            List<User> users = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getResultList();

            if (users.isEmpty()) return null;

            User user = users.get(0);
            if (!encoder.matches(password, user.getPassword())) {
                return null;
            }

            return user;
        } catch (Exception e) {
            throw new UserException("Login failed", e);
        }
    }

    @Override
    public User findByName(String name) throws UserException {
        try {
            List<User> users = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getResultList();

            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            throw new UserException("Find failed", e);
        }
    }

    @Override
    public void reset() throws UserException {
        try {
            entityManager.createNativeQuery("DELETE FROM User").executeUpdate();
        } catch (Exception e) {
            throw new UserException("Reset failed", e);
        }
    }
}