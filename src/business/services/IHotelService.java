package business.services;

import core.utilities.results.SuccessInformationResult;
import entity.Hotel;

import java.util.ArrayList;

public interface IHotelService {
    SuccessInformationResult create(Hotel hotel);
    SuccessInformationResult update(Hotel hotel);
    SuccessInformationResult delete(int id);
    Hotel getById(int id);
    ArrayList<Hotel> getAll();
    ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> modelList);
}
