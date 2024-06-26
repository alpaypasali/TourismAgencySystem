package dao.Abstract;

import entity.Reservation;

import java.util.ArrayList;

public interface IReservationDal {
    boolean create(Reservation reservation);
    boolean update(Reservation reservation);
    boolean delete(int id);
    Reservation getById(int id);
    ArrayList<Reservation> getAll();
    ArrayList<Reservation> selectByQuery(String query);
}
