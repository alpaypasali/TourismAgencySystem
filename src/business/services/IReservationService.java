package business.services;

import core.utilities.results.SuccessInformationResult;
import entity.PensionType;
import entity.Reservation;
import entity.Room;

import java.util.ArrayList;

public interface IReservationService {
    SuccessInformationResult create(Reservation reservation);
    SuccessInformationResult update(Reservation reservation );
    SuccessInformationResult delete(int id);
    Reservation getById(int id);
    ArrayList<Reservation> getAll();
    ArrayList<Object[]> getForTable(int size , ArrayList<Reservation> reservations);
    ArrayList<Reservation> getAllByRoomId(int roomId);
}
