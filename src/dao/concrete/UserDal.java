package dao.concrete;

import dao.Abstract.IUserDal;
import dao.Db.Db;
import entity.User;
import entity.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDal implements IUserDal {

    private  final Connection conn;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.users ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.users (username, password, role) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE public.users SET username = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.users WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.users WHERE id = ?";
    private static final String SELECT_Sign_In = "Select * from public.users where username = ? And password = ?";
    public UserDal() {
        this.conn = Db.getInstance();
    }

    @Override
    public User signIn(String username, String password) {
        User user = null;
        try( PreparedStatement pr = this.conn.prepareStatement(SELECT_Sign_In)){

            pr.setString(1,username);
            pr.setString(2,password);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                user = this.extractUser(rs);

            }
        }
        catch (SQLException e){

            e.printStackTrace();
        }
        return  user;
    }

    @Override
    public boolean create(User user) {
        return  executeUpdate(INSERT_QUERY , user);
    }

    @Override
    public boolean update(User user) {
        return  executeUpdate(UPDATE_QUERY , user);
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
    public User getById(int id) {
        User user = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                user = extractUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public ArrayList<User> selectByQuery(String query) {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                users.add(this.extractUser(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return users;
    }

    private boolean executeUpdate(String query, User user) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setString(1, user.getUsername());
            pr.setString(2, user.getPassword());
            pr.setString(3, user.getRole().toString());
            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(4, user.getId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(Role.valueOf(rs.getString("role")));

        return user;
    }
}
