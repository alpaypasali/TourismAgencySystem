package dao.concrete;

import dao.Abstract.IPensionTypeDal;
import dao.Db.Db;
import entity.PensionType;
import entity.User;
import entity.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PensionTypeDal implements IPensionTypeDal {


    private  final Connection conn;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.pension_types ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.pension_types (name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE public.pension_types SET name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.pension_types WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.pension_types WHERE id = ?";

    public PensionTypeDal() {
        this.conn = Db.getInstance();
    }

    @Override
    public boolean create(PensionType pensionType) {
        return  executeUpdate(INSERT_QUERY , pensionType);
    }

    @Override
    public boolean update(PensionType pensionType) {
        return  executeUpdate(UPDATE_QUERY , pensionType);
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
    public PensionType getById(int id) {
        PensionType pensionType = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                pensionType = extractPensionType(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionType;
    }

    @Override
    public ArrayList<PensionType> getAll() {
        ArrayList<PensionType> pensionTypes = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                pensionTypes.add(extractPensionType(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionTypes;
    }

    @Override
    public ArrayList<PensionType> selectByQuery(String query) {
        ArrayList<PensionType> pensionTypes = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                pensionTypes.add(this.extractPensionType(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return pensionTypes;
    }


    private boolean executeUpdate(String query, PensionType pensionType) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setString(1, pensionType.getName());
            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(2, pensionType.getId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PensionType extractPensionType(ResultSet rs) throws SQLException {
       PensionType pensionType = new PensionType();
        pensionType.setId(rs.getInt("id"));
        pensionType.setName(rs.getString("name"));
        return pensionType;
    }
}
