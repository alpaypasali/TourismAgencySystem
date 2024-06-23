package business.services;

import core.utilities.results.SuccessInformationResult;
import entity.RoomType;


import java.util.ArrayList;

public interface IRoomTypeService {

    SuccessInformationResult create(RoomType roomType);
    SuccessInformationResult update(RoomType roomType);
    SuccessInformationResult delete(int id);
    RoomType getById(int id);
    ArrayList<RoomType> getAll();
    ArrayList<Object[]> getForTable(int size);
}
