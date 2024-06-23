package business.services;

import core.utilities.results.SuccessDataResult;
import core.utilities.results.SuccessInformationResult;
import entity.User;
import entity.enums.Role;

import java.util.ArrayList;

public interface IUserService {

    SuccessDataResult<User> signIn(String username, String password);
    SuccessInformationResult create(User user);
    SuccessInformationResult update(User user);
    SuccessInformationResult delete(int id);
    User getById(int id);
    ArrayList<User> getAll();
    ArrayList<Object[]> getForTable(int size, ArrayList<User> modelList);
    ArrayList<User> searchForTable(Role role );
}
