package dao.concrete;

import dao.Abstract.IHotelDal;
import dao.Abstract.IRoomDal;
import dao.Abstract.IRoomTypeDal;
import dao.Db.Db;
import entity.PensionType;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDal implements IRoomDal {
    private  final Connection conn;
    private IHotelDal hotelDal;
    private IRoomTypeDal roomTypeDal;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.rooms ORDER BY room_id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.rooms (hotel_id, room_type_id, adult_price, child_price, stock, bed_count, square_meters, television, minibar, game_console, safe, projection) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE public.rooms SET hotel_id = ?, room_type_id = ?, adult_price = ?, child_price = ?, stock = ?, bed_count = ?, square_meters = ?, television = ?, minibar = ?, game_console = ?, safe = ?, projection = ? WHERE room_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.rooms WHERE room_id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.rooms WHERE room_id = ?";


    public RoomDal() {
        this.conn = Db.getInstance();
        this.hotelDal = new HotelDal();
        this.roomTypeDal = new RoomTypeDal();
    }
    @Override
    public boolean create(Room room) {
        return  executeUpdate(INSERT_QUERY , room);
    }

    @Override
    public boolean update(Room room) {
        return  executeUpdate(UPDATE_QUERY , room);
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
    public Room getById(int id) {
        Room room = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                room = extractRoom(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }
    public ArrayList<Room> getRoomsByHotelId(int hotelId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM public.rooms WHERE hotel_id = ? AND stock > 0";
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                rooms.add(extractRoom2(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    @Override
    public ArrayList<Room> getAll() {
        ArrayList<Room> roomArrayList = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                roomArrayList.add(extractRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomArrayList;
    }

    @Override
    public ArrayList<Room> selectByQuery(String query) {
        ArrayList<Room> roomArrayList = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                roomArrayList.add(this.extractRoom(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return roomArrayList;
    }
    private boolean executeUpdate(String query, Room room) {
        try (PreparedStatement statement  = conn.prepareStatement(query)) {
            statement.setInt(1, room.getHotelId());
            statement.setInt(2, room.getRoomTypeId());
            statement.setInt(3, room.getAdultPrice());
            statement.setInt(4, room.getChildPrice());
            statement.setInt(5, room.getStock());
            statement.setInt(6, room.getBedCount());
            statement.setInt(7, room.getSquareMeters());
            statement.setBoolean(8, room.getTelevision());
            statement.setBoolean(9, room.getMinibar());
            statement.setBoolean(10, room.getGameConsole());
            statement.setBoolean(11, room.getSafe());
            statement.setBoolean(12, room.getProjection());
            if (query.equals(UPDATE_QUERY)) {
                statement.setInt(13, room.getRoomId());
            }
            return statement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Room extractRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setHotelId(rs.getInt("hotel_id"));
        room.setHotel(this.hotelDal.getById(rs.getInt("hotel_id")));
        room.setRoomTypeId(rs.getInt("room_type_id"));
        room.setRoomType(this.roomTypeDal.getById(rs.getInt("room_type_id")));
        room.setAdultPrice(rs.getInt("adult_price"));
        room.setChildPrice(rs.getInt("child_price"));
        room.setStock(rs.getInt("stock"));
        room.setBedCount(rs.getInt("bed_count"));
        room.setSquareMeters(rs.getInt("square_meters"));
        room.setTelevision(rs.getBoolean("television"));
        room.setMinibar(rs.getBoolean("minibar"));
        room.setGameConsole(rs.getBoolean("game_console"));
        room.setSafe(rs.getBoolean("safe"));
        room.setProjection(rs.getBoolean("projection"));
        return room;
    }
    private Room extractRoom2(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setHotelId(rs.getInt("hotel_id"));
        room.setRoomTypeId(rs.getInt("room_type_id"));
        room.setRoomType(this.roomTypeDal.getById(rs.getInt("room_type_id")));
        room.setAdultPrice(rs.getInt("adult_price"));
        room.setChildPrice(rs.getInt("child_price"));
        room.setStock(rs.getInt("stock"));
        room.setBedCount(rs.getInt("bed_count"));
        room.setSquareMeters(rs.getInt("square_meters"));
        room.setTelevision(rs.getBoolean("television"));
        room.setMinibar(rs.getBoolean("minibar"));
        room.setGameConsole(rs.getBoolean("game_console"));
        room.setSafe(rs.getBoolean("safe"));
        room.setProjection(rs.getBoolean("projection"));
        return room;
    }
}
