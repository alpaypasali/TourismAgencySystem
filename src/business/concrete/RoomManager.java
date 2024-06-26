package business.concrete;

import business.services.IRoomService;
import business.services.ISeasonService;
import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.exceptions.ValidationException;
import core.utilities.handlers.ErrorHandler;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.IRoomDal;
import dao.Abstract.ISeasonsDal;
import dao.concrete.RoomDal;
import entity.PensionType;
import entity.Room;
import entity.Season;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RoomManager implements IRoomService {
    private IRoomDal roomDal;
    private ISeasonService seasonService;
    public RoomManager(){
        this.roomDal = new RoomDal();
        this.seasonService = new SeasonManager();
    }
    @Override
    public SuccessInformationResult create(Room room) {
        try {

            RoomCannotBeEmpty(room);
            StockCannotbeSmallerThanZero(room.getStock());
            boolean createdPensionType = roomDal.create(room);
            if(createdPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Created Room successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult update(Room room) {
        try {

            RoomCannotBeEmpty(room);
            StockCannotbeSmallerThanZero(room.getStock());
            boolean createdPensionType = roomDal.update(room);
            if(createdPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Updated Room successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult delete(int id) {
        try {

            RoomCannotBeEmpty(id);
            boolean deletedPensionType = roomDal.delete(id);
            if(deletedPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Deleted Room successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public Room getById(int id) {
        try{
            Room room = roomDal.getById(id);
            this.RoomCannotBeEmpty(room);
            return  room;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Room> getAll() {
        ArrayList<Room> roomArrayList = roomDal.getAll();
        if(roomArrayList.isEmpty())
            return  null;

        return roomArrayList;
    }
    @Override
    public ArrayList<Room> getAllByHotelId(int hotelId) {
        ArrayList<Room> roomArrayList = roomDal.getAll();
        if (roomArrayList.isEmpty())
            return null;

        // Use stream and filter to get rooms with the specified hotel ID
        ArrayList<Room> filteredRooms = roomArrayList.stream()
                .filter(room -> room.getHotelId() == hotelId)
                .collect(Collectors.toCollection(ArrayList::new));

        return filteredRooms.isEmpty() ? null : filteredRooms;
    }

    public ArrayList<Room> getRoomsByHotelId(int hotelId, int roomId) {
        ArrayList<Room> allRooms = getAll(); // getAll() metodunu kullanarak tüm odaları al

        if (allRooms == null || allRooms.isEmpty()) {
            return new ArrayList<>(); // Eğer tüm odalar null veya boş ise boş bir liste döndür
        }

        // Lambda ve stream kullanarak filtreleme yap
        ArrayList<Room> filteredRooms = allRooms.stream()
                .filter(room -> room.getHotelId() == hotelId && (room.getRoomId() == roomId || room.getStock() > 0))
                .collect(Collectors.toCollection(ArrayList::new));

        return filteredRooms;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> roomArrayList) {
        ArrayList<Object[]> arrayList = new ArrayList<>();
        if (roomArrayList == null) {
            return arrayList; // Return empty list if modelList is null
        }
        for (Room u : roomArrayList) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = u.getRoomId();
            rowObject[i++] = u.getHotel().getHotelName();
            rowObject[i++] = u.getHotel().getPensionType().getName();
                Season season = seasonService.getByHotelId(u.getHotelId());
                if (season != null) {
                    LocalDate startDate = season.getStart_date();
                    LocalDate endDate = season.getEnd_date();
                    rowObject[i++] = startDate.toString() + " - " + endDate.toString();
                } else {
                    rowObject[i++] = "";
                }
            rowObject[i++] = u.getRoomType().getName();
            rowObject[i++] = u.getStock();
            rowObject[i++] = u.getAdultPrice();
            rowObject[i++] = u.getChildPrice();
            rowObject[i++] = u.getBedCount();
            rowObject[i++] = u.getSquareMeters();
            arrayList.add(rowObject);
        }
        return arrayList;
    }


    private  void RoomCannotBeEmpty(Room room) throws BusinessException {

        if (room == null) {
            throw new BusinessException("Room cannot find");
        }

    }
    private  void RoomCannotBeEmpty(int id) throws BusinessException {
        Room room = roomDal.getById(id);
        if (room == null) {
            throw new BusinessException("Room cannot find");
        }

    }
    private  void StockCannotbeSmallerThanZero(int stock) throws ValidationException {
        if (stock <= 0) {
            throw new ValidationException("Stock cannot be smaller than zero");
        }


    }
}
