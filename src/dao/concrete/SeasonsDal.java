package dao.concrete;

import dao.Abstract.IHotelDal;
import dao.Abstract.ISeasonsDal;
import dao.Db.Db;
import entity.PensionType;
import entity.RoomType;
import entity.Season;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeasonsDal implements ISeasonsDal {
    private  final Connection conn;
    private IHotelDal hotelDal;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM seasons ORDER BY season_id ASC";
    private static final String INSERT_QUERY = "INSERT INTO seasons (hotel_id, start_date, end_date) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE seasons SET hotel_id = ?, start_date = ?, end_date = ? WHERE season_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM seasons WHERE season_id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM seasons WHERE season_id = ?";
    private static final String SELECT_BY_HotelID_QUERY = "SELECT * FROM seasons WHERE hotel_id = ?";

    public SeasonsDal() {
        this.conn = Db.getInstance();
        this.hotelDal = new HotelDal();
    }
    @Override
    public ArrayList<Season> getAll() {
        ArrayList<Season> seasons = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                seasons.add(extractSeason(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasons;
    }

    @Override
    public boolean create(Season seasons) {
        return  executeUpdate(INSERT_QUERY , seasons);
    }

    @Override
    public boolean update(Season seasons) {
        return  executeUpdate(UPDATE_QUERY , seasons);
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
    public Season getByid(int id) {
        Season season = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                season = extractSeason(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return season;
    }

    @Override
    public Season getByHotelid(int hotelId) {
        Season season = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_HotelID_QUERY)) {
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                season = extractSeason(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return season;
    }

    @Override
    public ArrayList<Season> selectByQuery(String query) {
        ArrayList<Season> seasons = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                seasons.add(this.extractSeason(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return seasons;
    }
    private boolean executeUpdate(String query, Season season) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, season.getHotel_id());
            pr.setDate(2, java.sql.Date.valueOf(season.getStart_date()));
            pr.setDate(3, java.sql.Date.valueOf(season.getEnd_date()));
            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(4, season.getSeason_id());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private Season extractSeason(ResultSet rs) throws SQLException {
        Season season = new Season();
        season.setSeason_id(rs.getInt("season_id"));
        season.setHotel_id(rs.getInt("hotel_id"));
        season.setStart_date(LocalDate.parse(rs.getString("start_date")));
        season.setEnd_date(LocalDate.parse(rs.getString("end_date")));
        season.setHotel(this.hotelDal.getById(rs.getInt("hotel_id")));
        return season;
    }

}
