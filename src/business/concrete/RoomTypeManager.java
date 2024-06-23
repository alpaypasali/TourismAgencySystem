package business.concrete;

import business.services.IRoomTypeService;
import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.handlers.ErrorHandler;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.IPensionTypeDal;
import dao.Abstract.IRoomTypeDal;
import dao.concrete.PensionTypeDal;
import dao.concrete.RoomTypeDal;
import entity.PensionType;
import entity.RoomType;

import java.util.ArrayList;

public class RoomTypeManager implements IRoomTypeService {
    private IRoomTypeDal roomTypeDal;

    public RoomTypeManager() {
        this.roomTypeDal = new RoomTypeDal();
    }
    @Override
    public SuccessInformationResult create(RoomType roomType) {
        try {

            RoomTypeCannotBeEmpty(roomType);
            boolean createdRoomType = roomTypeDal.create(roomType);
            if(createdRoomType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Created RoomType successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult update(RoomType roomType) {
        try {

            RoomTypeCannotBeEmpty(roomType);
            boolean createdRoomType = roomTypeDal.update(roomType);
            if(createdRoomType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Updated RoomType successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult delete(int id) {
        try {

            RoomTypeCannotBeEmpty(id);
            boolean deletedRoomType = roomTypeDal.delete(id);
            if(deletedRoomType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Deleted RoomType successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public RoomType getById(int id) {
        try{
            RoomType roomType = roomTypeDal.getById(id);
            this.RoomTypeCannotBeEmpty(roomType);
            return  roomType;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<RoomType> getAll() {
        ArrayList<RoomType> roomTypes = roomTypeDal.getAll();
        if(roomTypes.isEmpty())
            return  null;

        return roomTypes;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        for (RoomType u : this.getAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = u.getId();
            rowObject[i++] = u.getName();
            arrayList.add(rowObject);
        }
        return arrayList;
    }
    private  void RoomTypeCannotBeEmpty(RoomType roomType) throws BusinessException {

        if (roomType == null) {
            throw new BusinessException("RoomType cannot find");
        }

    }
    private  void RoomTypeCannotBeEmpty(int id) throws BusinessException {
        RoomType roomType = roomTypeDal.getById(id);
        if (roomType == null) {
            throw new BusinessException("RoomType cannot find");
        }


    }
}
