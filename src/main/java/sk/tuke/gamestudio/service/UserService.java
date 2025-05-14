package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

public interface UserService {
    void signUp(User user);
    User loginUser(String name, String password);
    void reset();
    User findByName(String name);
}
