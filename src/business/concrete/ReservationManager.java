package business.concrete;

import business.services.IReservationService;
import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.handlers.ErrorHandler;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.IReservationDal;
import dao.concrete.ReservationDal;
import entity.PensionType;
import entity.Reservation;
import entity.Room;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReservationManager implements IReservationService {
    private IReservationDal reservationDal;
    public ReservationManager(){
        reservationDal = new ReservationDal();
    }
    @Override
    public SuccessInformationResult create(Reservation reservation) {
        try {

            ReservationCannotBeEmpty(reservation);
            boolean createdPensionType = reservationDal.create(reservation);
            if(createdPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Created reservation successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult update(Reservation reservation) {
        try {

            ReservationCannotBeEmpty(reservation);
            boolean createdPensionType = reservationDal.update(reservation);
            if(createdPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Updated reservation successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult delete(int id) {
        try {

            ReservationCannotBeEmpty(id);
            boolean deletedReservation = reservationDal.delete(id);
            if(deletedReservation == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Deleted Reservation successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public Reservation getById(int id) {
        try{
            Reservation reservation = reservationDal.getById(id);
            this.ReservationCannotBeEmpty(reservation);
            return  reservation;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservations = reservationDal.getAll();
        if(reservations.isEmpty())
            return  null;

        return reservations;
    }
    @Override
    public ArrayList<Reservation> getAllByRoomId(int roomId) {
        ArrayList<Reservation> reservations = reservationDal.getAll();
        if (reservations.isEmpty())
            return null;

        // Use stream and filter to get rooms with the specified hotel ID
        ArrayList<Reservation> filteredReservations = reservations.stream()
                .filter(reservation -> reservation.getRoomId() == roomId)
                .collect(Collectors.toCollection(ArrayList::new));

        return filteredReservations.isEmpty() ? null : filteredReservations;
    }
    @Override
    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservations) {
        ArrayList<Object[]> arrayList = new ArrayList<>();
        if (reservations == null) {
            return arrayList; // Return empty list if modelList is null
        }
        for (Reservation u : this.getAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = u.getReservationId();
            rowObject[i++] = u.getHotel().getHotelName();
            rowObject[i++] = u.getRoom().getRoomType().getName();
            rowObject[i++] = u.getCustomerName();
            rowObject[i++] = u.getCustomerId();
            rowObject[i++] = u.getCustomerPhone();
            rowObject[i++] = u.getCustomerEmail();
            rowObject[i++] = u.getAdultCount();
            rowObject[i++] = u.getChildCount();
            rowObject[i++] = u.getStartDate();
            rowObject[i++] = u.getEndDate();
            rowObject[i++] = u.getTotalPrice();

            arrayList.add(rowObject);
        }
        return arrayList;
    }

    private  void ReservationCannotBeEmpty(Reservation reservation) throws BusinessException {

        if (reservation == null) {
            throw new BusinessException("Reservation cannot find");
        }

    }
    private  void ReservationCannotBeEmpty(int id) throws BusinessException {
        Reservation reservation = reservationDal.getById(id);
        if (reservation == null) {
            throw new BusinessException("Reservation cannot find");
        }

    }
}
