package business.concrete;

import business.services.IHotelService;
import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.handlers.ErrorHandler;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.IHotelDal;
import dao.concrete.HotelDal;
import entity.Hotel;

import java.util.ArrayList;

public class HotelManager implements IHotelService {
    private IHotelDal hotelDal;
    public HotelManager(){
        hotelDal = new HotelDal();
    }
    @Override
    public SuccessInformationResult create(Hotel hotel) {
        try {

            HotelCannotBeEmpty(hotel);
            boolean createdHotel = hotelDal.create(hotel);
            if(createdHotel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Created Hotel successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult update(Hotel hotel) {
        try {

            HotelCannotBeEmpty(hotel);
            boolean createdHotel = hotelDal.update(hotel);
            if(createdHotel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Updated Hotel successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult delete(int id) {
        try {

            HotelCannotBeEmpty(id);
            boolean deletedHotel = hotelDal.delete(id);
            if(deletedHotel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Deleted Hotel successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public Hotel getById(int id) {
        try{
            Hotel hotel = hotelDal.getById(id);
            this.HotelCannotBeEmpty(hotel);
            return  hotel;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Hotel> getAll() {
        ArrayList<Hotel> hotels = hotelDal.getAll();
        if(hotels.isEmpty())
            return  null;

        return hotels;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> modelList) {
        ArrayList<Object[]> modelObjList = new ArrayList<>();
        if (modelList == null) {
            return modelObjList; // Return empty list if modelList is null
        }
        for (Hotel u : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = u.getHotelId();
            rowObject[i++] = u.getHotelName();
            rowObject[i++] = u.getPensionType().getName();
            rowObject[i++] = u.getCity();
            rowObject[i++] = u.getDistrict();
            rowObject[i++] = u.getFullAddress();
            rowObject[i++] = u.getEmail();
            rowObject[i++] = u.getHas24_7RoomService();
            rowObject[i++] = u.getHasFreeParking();
            rowObject[i++] = u.getHasSpa();
            modelObjList.add(rowObject);
        }
        return modelObjList;
    }

    private  void HotelCannotBeEmpty(Hotel hotel) throws BusinessException {

        if (hotel == null) {
            throw new BusinessException("Hotel cannot find");
        }

    }
    private  void HotelCannotBeEmpty(int id) throws BusinessException {
        Hotel hotel = hotelDal.getById(id);
        if (hotel == null) {
            throw new BusinessException("Hotel cannot find");
        }

    }
}
