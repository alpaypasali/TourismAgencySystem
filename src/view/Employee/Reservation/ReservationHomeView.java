package view.Employee.Reservation;

import business.concrete.ReservationManager;
import business.concrete.RoomManager;
import business.services.IReservationService;
import business.services.IRoomService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Reservation;
import entity.Room;
import view.AdminLayout;
import view.Employee.Room.UpdateRoomView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ReservationHomeView extends AdminLayout {


    private JPanel container;
    private JTable tbl_reservations;
    private IReservationService reservationService;
    private Reservation reservation;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JPopupMenu reservationMenu;
    private Object[] columnNames;
    private IRoomService roomService;
    public ReservationHomeView(){
        this.add(container);
        this.reservation = new Reservation();
        this.reservationService = new ReservationManager();
        this.roomService = new RoomManager();
        this.reservationMenu = new JPopupMenu();
        FrameHelper.setupFrame(this,989, 555, "Alpay Tourism Agency");
        this.tbl_reservations.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_reservations.rowAtPoint(e.getPoint());
                if (selectedRow >= 0 && SwingUtilities.isRightMouseButton(e)) {
                    tbl_reservations.setRowSelectionInterval(selectedRow, selectedRow);
                    reservationMenu.show(tbl_reservations, e.getX(), e.getY());
                }
            }
        });
        CreateTable(null);
        UpdateReservation();
        DeleteReservation();

    }
    public void CreateTable(ArrayList<Object[]> modelList) {
        this.columnNames = new Object[] {
                "Reservation ID",
                "Hotel Name",
                "Room Type",
                "Customer Name",
                "Identification",
                "Customer Phone",
                "Customer Email",
                "Adult",
                "Child",
                "Start Date",
                "End Date",
                "Total Price"
        };

        if (modelList == null || modelList.isEmpty()) {
            modelList = reservationService.getForTable(this.columnNames.length, this.reservationService.getAll());
            if (modelList == null || modelList.isEmpty()) {
                System.out.println("The table is empty. No data available.");
                modelList = new ArrayList<>();
                modelList.add(new Object[]{"No data", "", "", "", "", "", ""});
            }
        }

        createTable(defaultTableModel, tbl_reservations, columnNames, modelList);
    }
    public void UpdateReservation(){
        this.reservationMenu.add("Update").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbl_reservations.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedId = Integer.parseInt(tbl_reservations.getValueAt(selectedRow, 0).toString());
                    Reservation selectedReservation = reservationService.getById(selectedId);
                    EditReservationView editReservationView = new EditReservationView(selectedReservation);
                    editReservationView.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            CreateTable(null);


                        }
                    });


                }
            }
        });


    }
    public void DeleteReservation(){

        this.reservationMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_reservations.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_reservations.getValueAt(selectedRow, 0).toString());


                SuccessInformationResult reservationDeleted =  this.reservationService.delete(selectedId);
                Room room = roomService.getById(reservation.getRoomId());
                room.setStock(room.getStock() + 1);
                roomService.update(room);
                reservationDeleted.showMessageDialog();
                CreateTable(null);



            }
        });


    }
}
