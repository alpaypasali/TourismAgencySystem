package business.services;

import core.utilities.results.SuccessInformationResult;
import entity.Room;
import entity.Season;

import java.util.ArrayList;

public interface IRoomService {
    SuccessInformationResult create(Room room);
    SuccessInformationResult update(Room room);
    SuccessInformationResult delete(int id);
    Room getById(int id);
    ArrayList<Room> getAll();
    ArrayList<Object[]> getForTable(int size , ArrayList<Room> roomArrayList);
}
