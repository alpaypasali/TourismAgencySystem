package dao.Abstract;

import entity.Room;
import entity.RoomType;

import java.util.ArrayList;

public interface IRoomDal {

    boolean create(Room room);
    boolean update(Room room);
    boolean delete(int id);
    Room getById(int id);
    ArrayList<Room> getAll();
    ArrayList<Room> selectByQuery(String query);
    ArrayList<Room> getRoomsByHotelId(int hotelId);
}
