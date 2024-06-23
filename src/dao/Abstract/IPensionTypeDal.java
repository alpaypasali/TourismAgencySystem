package dao.Abstract;

import entity.PensionType;
import entity.User;

import java.util.ArrayList;

public interface IPensionTypeDal {
    boolean create(PensionType pensionType);
    boolean update(PensionType pensionType);
    boolean delete(int id);
    PensionType getById(int id);
    ArrayList<PensionType> getAll();
    ArrayList<PensionType> selectByQuery(String query);
}
