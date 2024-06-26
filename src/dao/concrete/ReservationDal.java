package dao.concrete;

import dao.Abstract.IHotelDal;
import dao.Abstract.IReservationDal;
import dao.Abstract.IRoomDal;
import dao.Db.Db;
import entity.PensionType;
import entity.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDal implements IReservationDal {
    private  final Connection conn;
    private IHotelDal hotelDal;
    private IRoomDal roomDal;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.reservations ORDER BY reservation_id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.reservations (hotel_id, room_id, customer_name, customer_id, customer_phone, customer_email, total_price, start_date, end_date, adult_count, child_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE public.reservations SET hotel_id = ?, room_id = ?, customer_name = ?, customer_id = ?, customer_phone = ?, customer_email = ?, total_price = ?, start_date = ?, end_date = ?, adult_count = ?, child_count = ? WHERE reservation_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.reservations WHERE reservation_id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.reservations WHERE reservation_id = ?";


    public ReservationDal() {
        this.conn = Db.getInstance();
        this.hotelDal = new HotelDal();
        this.roomDal = new RoomDal();
    }
    @Override
    public boolean create(Reservation reservation) {
        return  executeUpdate(INSERT_QUERY , reservation);
    }

    @Override
    public boolean update(Reservation reservation) {
        return  executeUpdate(UPDATE_QUERY , reservation);
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement pr = conn.prepareStatement(DELETE_QUERY)) {
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Reservation getById(int id) {
        Reservation reservation = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                reservation = extractReservation(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                reservations.add(extractReservation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public ArrayList<Reservation> selectByQuery(String query) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                reservations.add(this.extractReservation(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return reservations;
    }

    private boolean executeUpdate(String query, Reservation reservation) {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reservation.getHotelId());
            pstmt.setInt(2, reservation.getRoomId());
            pstmt.setString(3, reservation.getCustomerName());
            pstmt.setString(4, reservation.getCustomerId());
            pstmt.setString(5, reservation.getCustomerPhone());
            pstmt.setString(6, reservation.getCustomerEmail());
            pstmt.setInt(7, reservation.getTotalPrice());
            pstmt.setDate(8, java.sql.Date.valueOf(reservation.getStartDate()));
            pstmt.setDate(9, java.sql.Date.valueOf(reservation.getEndDate()));
            pstmt.setInt(10, reservation.getAdultCount());
            pstmt.setInt(11, reservation.getChildCount());
            if (query.equals(UPDATE_QUERY)) {
                pstmt.setInt(12, reservation.getReservationId());
            }
            return pstmt.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Reservation extractReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(rs.getInt("reservation_id"));
        reservation.setHotelId(rs.getInt("hotel_id"));
        reservation.setHotel(this.hotelDal.getById(rs.getInt("hotel_id")));
        reservation.setRoomId(rs.getInt("room_id"));
        reservation.setRoom(this.roomDal.getById(rs.getInt("room_id")));
        reservation.setCustomerName(rs.getString("customer_name"));
        reservation.setCustomerId(rs.getString("customer_id"));
        reservation.setCustomerPhone(rs.getString("customer_phone"));
        reservation.setCustomerEmail(rs.getString("customer_email"));
        reservation.setTotalPrice(rs.getInt("total_price"));
        reservation.setStartDate(rs.getDate("start_date").toLocalDate());
        reservation.setEndDate(rs.getDate("end_date").toLocalDate());
        reservation.setAdultCount(rs.getInt("adult_count"));
        reservation.setChildCount(rs.getInt("child_count"));
        return reservation;
    }
}
