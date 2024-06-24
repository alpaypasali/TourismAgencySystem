package dao.concrete;

import dao.Abstract.IHotelDal;
import dao.Abstract.IPensionTypeDal;
import dao.Abstract.ISeasonsDal;
import dao.Db.Db;
import entity.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDal implements IHotelDal {

    private  final Connection conn;
    private   IPensionTypeDal pensionTypeDal;


    private static final String SELECT_ALL_QUERY = "SELECT * FROM hotels ORDER BY hotel_id ASC";
    private static final String INSERT_QUERY = "INSERT INTO hotels (hotel_name, city, district, full_address, email, phone, star_rating, has_free_parking, has_spa, has_24_7_room_service, pension_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE hotels SET hotel_name = ?, city = ?, district = ?, full_address = ?, email = ?, phone = ?, star_rating = ?, has_free_parking = ?, has_spa = ?, has_24_7_room_service = ?, pension_type_id = ? WHERE hotel_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM hotels WHERE hotel_id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM hotels WHERE hotel_id = ?";
    public HotelDal() {
        this.conn = Db.getInstance();
        this.pensionTypeDal = new PensionTypeDal();



    }
    @Override
    public boolean create(Hotel hotel) {
        return  executeUpdate(INSERT_QUERY , hotel);
    }

    @Override
    public boolean update(Hotel hotel) {
        return  executeUpdate(UPDATE_QUERY , hotel);
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
    public Hotel getById(int id) {
        Hotel hotel = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                hotel = extractHotel(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotel;
    }

    @Override
    public ArrayList<Hotel> getAll() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                hotels.add(extractHotel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public ArrayList<Hotel> selectByQuery(String query) {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                hotels.add(this.extractHotel(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return hotels;
    }

    private boolean executeUpdate(String query, Hotel hotel) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setString(1, hotel.getHotelName());
            pr.setString(2, hotel.getCity());
            pr.setString(3, hotel.getDistrict());
            pr.setString(4, hotel.getFullAddress());
            pr.setString(5, hotel.getEmail());
            pr.setString(6, hotel.getPhone());
            pr.setInt(7, hotel.getStarRating());
            pr.setBoolean(8, hotel.getHasFreeParking());
            pr.setBoolean(9, hotel.getHasSpa());
            pr.setBoolean(10, hotel.getHas24_7RoomService());
            pr.setInt(11, hotel.getPensionTypeId());

            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(12, hotel.getHotelId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private Hotel extractHotel(ResultSet resultSet) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(resultSet.getInt("hotel_id"));
        hotel.setHotelName(resultSet.getString("hotel_name"));
        hotel.setCity(resultSet.getString("city"));
        hotel.setDistrict(resultSet.getString("district"));
        hotel.setFullAddress(resultSet.getString("full_address"));
        hotel.setEmail(resultSet.getString("email"));
        hotel.setPhone(resultSet.getString("phone"));
        hotel.setStarRating(resultSet.getInt("star_rating"));
        hotel.setHasFreeParking(resultSet.getBoolean("has_free_parking"));
        hotel.setHasSpa(resultSet.getBoolean("has_spa"));
        hotel.setHas24_7RoomService(resultSet.getBoolean("has_24_7_room_service"));
        hotel.setPensionTypeId(resultSet.getInt("pension_type_id"));
        hotel.setPensionType(this.pensionTypeDal.getById(resultSet.getInt("pension_type_id")));


        return hotel;
    }
}
