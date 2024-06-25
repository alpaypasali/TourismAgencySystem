package view.Employee.Reservation;

import business.concrete.RoomManager;
import business.services.IRoomService;
import core.utilities.helpers.ComboItem;
import core.utilities.helpers.FrameHelper;
import entity.Hotel;
import entity.Room;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InfoHotelView extends JFrame{
    private JPanel container;
    private JCheckBox spaCheckBox;
    private JCheckBox a724RoomServiceCheckBox;
    private JCheckBox parkAreaCheckBox;
    private JTextField txt_city;
    private JLabel lbl_hotelName;
    private JPanel pnl_roomType;
    private JComboBox cmb_roomType;
    private JFormattedTextField txt_startDate;
    private JFormattedTextField txt_endDate;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JCheckBox televisionCheckBox;
    private JCheckBox gameConsoleCheckBox;
    private JCheckBox minibarCheckBox;
    private JCheckBox safeCheckBox;
    private JCheckBox projectionCheckBox;
    private JLabel lbl_bedCount;
    private JLabel lbl_Stars;
    private JPanel pnl_room;
    private JLabel lbl_square;
    private JLabel lbl_city;
    private JLabel lbl_fullAddress;
    private JLabel lbl_phone;
    private JLabel lbl_email;
    private JLabel lbl_discrtict;
    private JButton createReservationButton;
    private JTextField txt_totalPrice;
    private Hotel hotel;
    private IRoomService roomService;
    public InfoHotelView(Hotel hotel){
        this.add(container);
        this.hotel = hotel;
        this.roomService = new RoomManager();
        FrameHelper.setupFrame(this,989, 555, "Alpay Tourism Agency");
        lbl_hotelName.setText(hotel.getHotelName() + " - " + hotel.getPensionType().getName());
        lbl_city.setText(hotel.getCity());
lbl_discrtict.setText(hotel.getDistrict());
        lbl_fullAddress.setText(hotel.getFullAddress());
        lbl_phone.setText(hotel.getPhone());
        lbl_email.setText(hotel.getEmail());

        spaCheckBox.setSelected(hotel.getHasSpa());
        parkAreaCheckBox.setSelected(hotel.getHasFreeParking());
        a724RoomServiceCheckBox.setSelected(hotel.getHas24_7RoomService());
        lbl_Stars.setText(hotel.getStarRating() + " Stars");
        ArrayList<Room> hotelRooms = hotel.getRooms();
        for (Room room :hotelRooms )
        {
            this.cmb_roomType.addItem(room.getComboItem());
        }
        cmb_roomType.setSelectedItem(null);


        cmb_roomType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cmb_roomType.getSelectedItem() == null){
                    pnl_room.enable(false);
                }
                else{
                    pnl_room.enable(true);
                    ComboItem selectedRoomType = (ComboItem) cmb_roomType.getSelectedItem();
                   Room room = roomService.getById(selectedRoomType.getKey());
                   lbl_bedCount.setText(room.getBedCount().toString());
                   lbl_square.setText(room.getSquareMeters().toString());
                   televisionCheckBox.setSelected(room.getTelevision());
                   minibarCheckBox.setSelected(room.getMinibar());
                   gameConsoleCheckBox.setSelected(room.getGameConsole());
                   safeCheckBox.setSelected(room.getSafe());
                   projectionCheckBox.setSelected(room.getProjection());

                }

            }
        });


        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() throws ParseException {
        // TODO: place custom component creation code here
        // TODO: place custom component creation code here
        this.txt_startDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txt_endDate = new JFormattedTextField(new MaskFormatter("##/##/####"));

        // Bugünün tarihini al ve formatla
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        // Alanlara bugünün tarihini ata
        this.txt_startDate.setText(formattedDate);
        this.txt_endDate.setText(formattedDate);

    }
}
