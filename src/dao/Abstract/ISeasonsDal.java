package dao.Abstract;

import entity.Season;

import java.util.ArrayList;

public interface ISeasonsDal {
    ArrayList<Season> getAll();
    boolean create(Season seasons);
    boolean update(Season seasons);
    boolean delete(int id);
    Season getByid(int id);
    Season getByHotelid(int hotelId);
    ArrayList<Season> selectByQuery(String query);

}
