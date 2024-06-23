package view.Admin;

import business.concrete.UserManager;
import business.services.IUserService;
import core.utilities.results.SuccessInformationResult;
import entity.User;
import entity.enums.Role;
import view.AdminLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminHomeView extends AdminLayout {
    private JPanel container;
    private JTable tbl_users;
    private JButton btn_create;
    private JComboBox<Role> cmb_role;
    private JButton clearButton;
    private IUserService userService;
    private User user;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private  JPopupMenu adminMenu;
    Object[] columnNames;

    public AdminHomeView(User user){
        this.add(container);
        this.user = user;
        this.userService = new UserManager();
        this.adminMenu = new JPopupMenu();
        guiIntilaze();

        this.tbl_users.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_users.rowAtPoint(e.getPoint());
                tbl_users.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    adminMenu.show(tbl_users, e.getX(), e.getY());
                }
            }
        });

        CreateTable(null);
        DeleteUser();
        UpdateUser();
        CreateUser();
        LoadFilter();
        GetFilter();
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateTable(null);
                cmb_role.setSelectedItem(null);
            }
        });
    }

    public void CreateTable(ArrayList<Object[]> modelList) {
        this.columnNames =new Object[] {"ID", "Username", "Password", "Role"};

        if (modelList == null || modelList.isEmpty()) {
            modelList = userService.getForTable(this.columnNames.length, this.userService.getAll());
            if (modelList == null || modelList.isEmpty()) {
                System.out.println("The table is empty. No data available.");
                modelList = new ArrayList<>();
                modelList.add(new Object[]{"No data", "", "", "", "", "", ""});
            }
        }

        createTable(defaultTableModel, tbl_users, columnNames, modelList);
    }
    public void CreateUser(){

        btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminCreateUserView adminCreateUserView = new AdminCreateUserView();
                adminCreateUserView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable(null);
                        GetFilter();
                    }
                });
            }
        });


    }
    public void UpdateUser() {

        this.adminMenu.add("Update").addActionListener(e -> {
            int selectedRow = tbl_users.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_users.getValueAt(selectedRow, 0).toString());
                User selectedUser = this.userService.getById(selectedId);
                AdminEditUserView editView = new AdminEditUserView(selectedUser);
                editView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable(null);
                        GetFilter();
                    }
                });
            }
        });


    }
    public void DeleteUser(){

        this.adminMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_users.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_users.getValueAt(selectedRow, 0).toString());

                SuccessInformationResult userDeleted =  this.userService.delete(selectedId);
                userDeleted.showMessageDialog();
                CreateTable(null);
                GetFilter();

            }
        });


    }
    public void LoadFilter(){

        this.cmb_role.setModel(new DefaultComboBoxModel<>(Role.values()));
        this.cmb_role.setSelectedItem(null);

    }

    public void GetFilter(){

        cmb_role.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ArrayList<User> userArrayList = userService.searchForTable(
                       (Role) cmb_role.getSelectedItem()

               );
               ArrayList<Object[]> userRowListBySearch = userService.getForTable(columnNames.length,userArrayList);
               CreateTable(userRowListBySearch);

            }
        });

    }
}
