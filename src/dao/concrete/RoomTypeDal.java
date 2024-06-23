package dao.concrete;

import dao.Abstract.IRoomTypeDal;
import dao.Db.Db;
import entity.PensionType;
import entity.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomTypeDal implements IRoomTypeDal {

    private  final Connection conn;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.room_types ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.room_types (name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE public.room_types SET name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.room_types WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.room_types WHERE id = ?";

    public RoomTypeDal() {
        this.conn = Db.getInstance();
    }
    @Override
    public boolean create(RoomType roomType) {
        return  executeUpdate(INSERT_QUERY , roomType);
    }

    @Override
    public boolean update(RoomType roomType) {
        return  executeUpdate(UPDATE_QUERY , roomType);
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
    public RoomType getById(int id) {
        RoomType roomType = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                roomType = extractRoomType(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomType;
    }

    @Override
    public ArrayList<RoomType> getAll() {
        ArrayList<RoomType> roomTypes = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                roomTypes.add(extractRoomType(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypes;
    }

    @Override
    public ArrayList<RoomType> selectByQuery(String query) {
        ArrayList<RoomType> roomTypes = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                roomTypes.add(this.extractRoomType(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return roomTypes;
    }


    private boolean executeUpdate(String query, RoomType roomType) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setString(1, roomType.getName());
            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(2, roomType.getId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private RoomType extractRoomType(ResultSet rs) throws SQLException {
        RoomType roomType = new RoomType();
        roomType.setId(rs.getInt("id"));
        roomType.setName(rs.getString("name"));
        return roomType;
    }
}
