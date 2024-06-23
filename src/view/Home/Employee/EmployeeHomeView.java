package view.Home.Employee;

import business.concrete.HotelManager;
import business.services.IHotelService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Hotel;
import entity.User;
import view.Admin.AdminCreateUserView;
import view.Admin.AdminEditUserView;
import view.AdminLayout;
import view.Employee.Hotel.CreateHotelView;
import view.Employee.Hotel.UpdateHotelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeHomeView extends AdminLayout {
    private JPanel container;
    private JTable tbl_hotels;
    private JScrollPane scr_tblHotels;
    private JButton manageRoomsButton;
    private JButton manageRezervationsButton;
    private JButton addHotelButton;
    private  User user;
    private Hotel hotel;
    private IHotelService hotelService;
    private  JPopupMenu hotelMenu;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    Object[] columnNames;
    public EmployeeHomeView(User user){
        this.add(container);
        this.hotelService = new HotelManager();
        this.hotelMenu = new JPopupMenu();
        this.user = user;
        FrameHelper.setupFrame(this,989, 555, "Alpay Tourism Agency");
        this.tbl_hotels.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotels.rowAtPoint(e.getPoint());
                tbl_hotels.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    hotelMenu.show(tbl_hotels, e.getX(), e.getY());
                }
            }
        });

        CreateTable(null);
        CreateHotel();
        UpdateHotel();
        DeleteHotel();



    }

    public void CreateTable(ArrayList<Object[]> modelList) {
        this.columnNames = new Object[] {
                "Hotel ID",
                "Hotel Name",
                "Pension Type",
                "City",
                "District",
                "Full Address",
                "Email",
                "24/7 Room Service",
                "Free Parking",
                "SPA"
        };

        if (modelList == null || modelList.isEmpty()) {
            modelList = hotelService.getForTable(this.columnNames.length, this.hotelService.getAll());
            if (modelList == null || modelList.isEmpty()) {
                System.out.println("The table is empty. No data available.");
                modelList = new ArrayList<>();
                modelList.add(new Object[]{"No data", "", "", "", "", "", ""});
            }
        }

        createTable(defaultTableModel, tbl_hotels, columnNames, modelList);
    }
    public void CreateHotel(){

        addHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateHotelView createHotelView = new CreateHotelView();
                createHotelView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable(null);

                    }
                });
            }
        });


    }
    public void UpdateHotel() {

        this.hotelMenu.add("Update").addActionListener(e -> {
            int selectedRow = tbl_hotels.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_hotels.getValueAt(selectedRow, 0).toString());
                Hotel selectedHotel = this.hotelService.getById(selectedId);
                UpdateHotelView editView = new UpdateHotelView(selectedHotel);
                editView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable(null);
                    }
                });
            }
        });


    }
    public void DeleteHotel(){

        this.hotelMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_hotels.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_hotels.getValueAt(selectedRow, 0).toString());

                SuccessInformationResult userDeleted =  this.hotelService.delete(selectedId);
                userDeleted.showMessageDialog();
                CreateTable(null);


            }
        });


    }
}
