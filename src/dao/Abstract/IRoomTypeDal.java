package dao.Abstract;

import entity.PensionType;
import entity.RoomType;

import java.util.ArrayList;

public interface IRoomTypeDal {
    boolean create(RoomType roomType);
    boolean update(RoomType roomType);
    boolean delete(int id);
    RoomType getById(int id);
    ArrayList<RoomType> getAll();
    ArrayList<RoomType> selectByQuery(String query);
}
