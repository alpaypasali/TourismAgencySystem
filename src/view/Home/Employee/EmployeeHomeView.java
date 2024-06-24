package view.Home.Employee;

import business.concrete.HotelManager;
import business.concrete.SeasonManager;
import business.services.IHotelService;
import business.services.ISeasonService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Hotel;
import entity.Season;
import entity.User;
import view.AdminLayout;
import view.Employee.Hotel.CreateHotelView;
import view.Employee.Hotel.UpdateHotelView;
import view.Employee.Room.RoomHomeView;
import view.Employee.Season.CreateSeasonView;
import view.Employee.Season.EditSeasonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EmployeeHomeView extends AdminLayout {
    private JPanel container;
    private JTable tbl_hotels;
    private JScrollPane scr_tblHotels;
    private JButton manageRoomsButton;
    private JButton manageReservationButton;
    private JButton addHotelButton;
    private JTable tbl_seasons;
    private JButton createSeasonButton;
    private  User user;
    private Hotel hotel;
    private IHotelService hotelService;
    private  JPopupMenu hotelMenu;
    private  JPopupMenu seasonMenu;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private DefaultTableModel defaultTableSeasonModel = new DefaultTableModel();
    private ISeasonService seasonService;
    Object[] columnNames;
    public EmployeeHomeView(User user){
        this.add(container);
        this.hotelService = new HotelManager();
        this.hotelMenu = new JPopupMenu();
        this.seasonMenu = new JPopupMenu();
        this.user = user;
        this.seasonService = new SeasonManager();
        FrameHelper.setupFrame(this,989, 555, "Alpay Tourism Agency");

        Menues();
        CreateHotelTable(null);
        CreateHotel();
        UpdateHotel();
        DeleteHotel();

        CreateSeason();
        CreateSeasonTable();
        UpdateSeason();
        DeleteSeason();


        manageRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomHomeView roomHomeView = new RoomHomeView();

            }
        });
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
    public void CreateHotel(){

        addHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateHotelView createHotelView = new CreateHotelView();
                createHotelView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateHotelTable(null);

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
                        CreateHotelTable(null);

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
                Season season = seasonService.getByHotelId(selectedId);
                if(season != null){
                    seasonService.delete(season.getSeason_id());
                }

                SuccessInformationResult userDeleted =  this.hotelService.delete(selectedId);
                userDeleted.showMessageDialog();
                CreateHotelTable(null);
                CreateSeasonTable();


            }
        });


    }




    public void CreateSeason(){
        this.hotelMenu.add("CreateSeason").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbl_hotels.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedId = Integer.parseInt(tbl_hotels.getValueAt(selectedRow, 0).toString());
                    if(!seasonService.checkIfHasSeason(selectedId)) {
                        Hotel selectedHotel = hotelService.getById(selectedId);

                        CreateSeasonView editView = new CreateSeasonView(selectedHotel);
                        editView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                CreateHotelTable(null);
                                CreateSeasonTable();

                            }
                        });

                    }else{
                        JOptionPane.showMessageDialog(null, "This hotel have a sesason", "İnformation", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });


    }
    public void UpdateSeason(){
        this.seasonMenu.add("Update").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbl_seasons.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedId = Integer.parseInt(tbl_seasons.getValueAt(selectedRow, 0).toString());
                        Season selectedSeason = seasonService.getById(selectedId);
                        EditSeasonView editView = new EditSeasonView(selectedSeason);
                        editView.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                CreateHotelTable(null);
                                CreateSeasonTable();

                            }
                        });


                }
            }
        });


    }
    public void DeleteSeason(){

        this.seasonMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_seasons.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_seasons.getValueAt(selectedRow, 0).toString());


                SuccessInformationResult seasonDeleted =  this.seasonService.delete(selectedId);
                seasonDeleted.showMessageDialog();
                CreateSeasonTable();



            }
        });


    }
    public void CreateSeasonTable(){
        Object[] columnNames = {"id", "hotel" , "start-date" , "end-date"};
        ArrayList<Object[]> arrayList = seasonService.getForTable(columnNames.length ,this.seasonService.getAll());
        if (arrayList == null || arrayList.isEmpty()) {
            System.out.println("The table is empty. No data available.");

            arrayList = new ArrayList<>();
            arrayList.add(new Object[]{"No data"});
        }
        createTable(defaultTableSeasonModel,tbl_seasons,columnNames,arrayList);
    }

    public void Menues(){

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
        this.tbl_seasons.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_seasons.rowAtPoint(e.getPoint());
                tbl_seasons.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    seasonMenu.show(tbl_seasons, e.getX(), e.getY());
                }
            }
        });


    }
}
