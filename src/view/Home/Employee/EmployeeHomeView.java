package view.Home.Employee;

import business.concrete.HotelManager;
import business.services.IHotelService;
import core.utilities.helpers.FrameHelper;
import entity.Hotel;
import entity.User;
import view.AdminLayout;
import view.Employee.Hotel.HotelHomeView;
import view.Employee.Room.RoomHomeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeHomeView extends AdminLayout {
    private JPanel container;
    private JTextField txt_searchHotelName;
    private JTextField txt_searchCity;
    private JButton searchButton;
    private JFormattedTextField txt_searchStartDate;
    private JFormattedTextField txt_searchEndDate;
    private JButton clearButton;
    private JTable tbl_hotels;
    private JButton btn_Hotel;
    private JButton btn_room;
    private JButton btn_reservation;
    private IHotelService hotelService;
    private User user;
    private Object[] columnNames;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JPopupMenu infoMenu;


    public EmployeeHomeView(User user){
        this.add(container);
        this.hotelService = new HotelManager();
        this.user = user;
        this.infoMenu = new JPopupMenu();
        FrameHelper.setupFrame(this,989, 555, "Alpay Tourism Agency");
        this.tbl_hotels.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotels.rowAtPoint(e.getPoint());
                tbl_hotels.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    infoMenu.show(tbl_hotels, e.getX(), e.getY());
                }
            }
        });



        CreateHotelTable(null);
        ManagmentMenues();
        Search();
        Clear();




    }
    public void CreateHotelTable(ArrayList<Object[]> modelList) {
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
    public void ManagmentMenues(){

        btn_Hotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelHomeView homeView = new HotelHomeView(user);
            }
        });
        btn_room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomHomeView roomHomeView = new RoomHomeView();
            }
        });


    }
    public void Search(){
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Arama parametrelerini al
                String hotelName = txt_searchHotelName.getText().trim();
                String city = txt_searchCity.getText().trim();




                // Arama işlemini yap ve tabloyu güncelle
                ArrayList<Hotel> hotels = hotelService.searchHotels(hotelName, city, txt_searchStartDate.getText(), txt_searchEndDate.getText());
                ArrayList<Object[]> modelList = hotelService.getForTable(columnNames.length , hotels);
                CreateHotelTable(modelList);
            }
        });



    }
    public void Clear(){
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateHotelTable(null);
            }
        });
    }
    private void createUIComponents() throws ParseException {
        // TODO: place custom component creation code here
        // TODO: place custom component creation code here
        this.txt_searchStartDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txt_searchEndDate = new JFormattedTextField(new MaskFormatter("##/##/####"));

        // Bugünün tarihini al ve formatla
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        // Alanlara bugünün tarihini ata
        this.txt_searchStartDate.setText(formattedDate);
        this.txt_searchEndDate.setText(formattedDate);
    }
}
