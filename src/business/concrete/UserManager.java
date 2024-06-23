package business.concrete;

import business.services.IUserService;
import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.exceptions.ValidationException;
import core.utilities.handlers.ErrorHandler;
import core.utilities.results.SuccessDataResult;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.IUserDal;
import dao.concrete.UserDal;
import entity.User;
import entity.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManager implements IUserService {

    private IUserDal userDal;
    public UserManager(){
        userDal = new UserDal();
    }
    @Override
    public SuccessDataResult<User> signIn(String username, String password) {
        try {
            validateCredentials(username, password);
            User user = userDal.signIn(username, password);
            UserCannotBeEmpty(user);
            SuccessDataResult<User> successMessage = new SuccessDataResult<>(user, "Login successful.");

            return successMessage;
        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;
        }
    }

    @Override
    public SuccessInformationResult create(User user) {
        try {

            UserCannotBeEmpty(user);
            boolean createdUser = userDal.create(user);
            if(createdUser == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Register successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult update(User user) {
        try {

            UserCannotBeEmpty(user);
            boolean updatedUSer = userDal.update(user);
            if(updatedUSer == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Update successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult delete(int id) {
        try {

            UserCannotBeEmpty(id);
            boolean deletedUSer = userDal.delete(id);
            if(deletedUSer == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Deleted User successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public User getById(int id) {
        try{
            User user = userDal.getById(id);
            this.UserCannotBeEmpty(user);
            return  user;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = userDal.getAll();
        if(users.isEmpty())
            return  null;

        return users;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size, ArrayList<User> modelList) {
        ArrayList<Object[]> modelObjList = new ArrayList<>();
        if (modelList == null) {
            return modelObjList; // Return empty list if modelList is null
        }
        for (User u : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = u.getId();
            rowObject[i++] = u.getUsername();
            rowObject[i++] = u.getPassword();
            rowObject[i++] = u.getRole();
            modelObjList.add(rowObject);
        }
        return modelObjList;
    }

    @Override
    public ArrayList<User> searchForTable(Role role) {
        ArrayList<User> users = userDal.getAll();
        if (users.isEmpty()) {
            return null;
        }


        if (role != null) {
            List<User> filteredUsers = users.stream()
                    .filter(user -> user.getRole().equals(role))
                    .collect(Collectors.toList());
            if (filteredUsers.isEmpty()) {
                return null;
            }
            return new ArrayList<>(filteredUsers);
        }

        return users;

    }

    private void validateCredentials(String username, String password) throws ValidationException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("password cannot be null or empty");
        }


    }

    private  void UserCannotBeEmpty(User user) throws BusinessException {

        if (user == null) {
            throw new BusinessException("User cannot find");
        }

    }
    private  void UserCannotBeEmpty(int id) throws BusinessException {
        User user = userDal.getById(id);
        if (user == null) {
            throw new BusinessException("User cannot find");
        }


    }
}
