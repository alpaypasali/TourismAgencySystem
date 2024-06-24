package dao.Abstract;

import entity.Hotel;
import entity.PensionType;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IHotelDal {

    boolean create(Hotel hotel);
    boolean update(Hotel hotel);
    boolean delete(int id);
    Hotel getById(int id);
    ArrayList<Hotel> getAll();
    ArrayList<Hotel> selectByQuery(String query);

}
