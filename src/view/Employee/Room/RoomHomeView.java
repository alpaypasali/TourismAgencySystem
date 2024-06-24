package view.Employee.Room;

import business.concrete.RoomManager;
import business.services.IRoomService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.IRoomDal;
import entity.Hotel;
import entity.Room;
import entity.Season;
import view.AdminLayout;
import view.Employee.Hotel.CreateHotelView;
import view.Employee.Hotel.UpdateHotelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RoomHomeView extends AdminLayout {
    private JPanel container;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTable tbl_rooms;
    private JButton searchButton;
    private JButton clearButton;
    private JButton addRoomButton;
    private Room room;
    private IRoomService roomService;
    Object[] columnNames;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();

    public RoomHomeView(){

        this.add(container);
        this.room = new Room();
        this.roomService = new RoomManager();
        FrameHelper.setupFrame(this,989, 555, "Alpay Tourism Agency");


        CreateTable(null);
        CreateRoom();

    }
    public void CreateTable(ArrayList<Object[]> modelList) {
        this.columnNames = new Object[] {
                "Room ID",
                "Hotel Name",
                "Pension Type",
                "Seasion",
                "Room Type",
                "Stock",
                "Adult Price",
                "Child Price",
                "Bed Capacity",
                "Square Meter"
        };

        if (modelList == null || modelList.isEmpty()) {
            modelList = roomService.getForTable(this.columnNames.length, this.roomService.getAll());
            if (modelList == null || modelList.isEmpty()) {
                System.out.println("The table is empty. No data available.");
                modelList = new ArrayList<>();
                modelList.add(new Object[]{"No data", "", "", "", "", "", ""});
            }
        }

        createTable(defaultTableModel, tbl_rooms, columnNames, modelList);
    }
    public void CreateRoom(){

        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateRoomView createRoomView = new CreateRoomView();
                createRoomView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable(null);

                    }
                });
            }
        });


    }

}
