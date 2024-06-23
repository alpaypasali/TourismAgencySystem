package dao.Abstract;

import entity.User;

import java.util.ArrayList;

public interface IUserDal {
    User signIn(String username, String password);
    boolean create(User user);
    boolean update(User user);
    boolean delete(int id);
    User getById(int id);
    ArrayList<User> getAll();
    ArrayList<User> selectByQuery(String query);
}
