package view.Employee.Reservation;

import business.concrete.RoomManager;
import business.services.IRoomService;
import core.utilities.helpers.ComboItem;
import core.utilities.helpers.FrameHelper;
import entity.Hotel;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private JSpinner spn_adult;
    private JSpinner spn_child;
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
    private Room room;
    private Reservation reservation;
    public InfoHotelView(Hotel hotel){
        this.add(container);
        this.hotel = hotel;
        this.reservation = new Reservation();
        this.room = new Room();
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
        if (cmb_roomType.getSelectedItem() == null) {
            pnl_room.setVisible(false);
        }



        cmb_roomType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmb_roomType.getSelectedItem() != null) {
                    pnl_room.setVisible(true);
                    ComboItem selectedRoomType = (ComboItem) cmb_roomType.getSelectedItem();
                    room = roomService.getById(selectedRoomType.getKey());
                    lbl_bedCount.setText(room.getBedCount().toString());
                    lbl_square.setText(room.getSquareMeters().toString());
                    televisionCheckBox.setSelected(room.getTelevision());
                    minibarCheckBox.setSelected(room.getMinibar());
                    gameConsoleCheckBox.setSelected(room.getGameConsole());
                    safeCheckBox.setSelected(room.getSafe());
                    projectionCheckBox.setSelected(room.getProjection());

                    int bedCount = room.getBedCount();
                    SpinnerNumberModel adultModel = new SpinnerNumberModel(0, 0, bedCount, 1);
                    spn_adult.setModel(adultModel);

                    SpinnerNumberModel childModel = new SpinnerNumberModel(0, 0, bedCount, 1);
                    spn_child.setModel(childModel);

                    spn_adult.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            updateSpinnerMaximum(spn_adult, spn_child, bedCount);
                            calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
                        }
                    });

                    spn_child.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            updateSpinnerMaximum(spn_child, spn_adult, bedCount);
                            calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
                        }
                    });

                    txt_startDate.addPropertyChangeListener("value", new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
                        }
                    });

                    txt_endDate.addPropertyChangeListener("value", new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
                        }
                    });
                }
            }
        });

        CreateReservation();



    }
    private  void CreateReservation(){


        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalPrice = Integer.parseInt(txt_totalPrice.getText().replace(",", ""));

                if(totalPrice < 1){

                    JOptionPane.showMessageDialog(null, "Please enter missing fields." , "İnformation", JOptionPane.WARNING_MESSAGE);
                }else{

                    reservation.setChildCount((int) spn_child.getValue());
                    reservation.setAdultCount((int) spn_adult.getValue());
                    reservation.setTotalPrice(totalPrice);
                    reservation.setStartDate(LocalDate.parse(txt_startDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    reservation.setEndDate(LocalDate.parse(txt_endDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                     CreateReservationView reservationView = new CreateReservationView(hotel , room , reservation);
                    reservationView.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            dispose();


                        }
                    });

                }

            }
        });
    }
    private void updateSpinnerMaximum(JSpinner spinner1, JSpinner spinner2, int max) {
        int value1 = (int) spinner1.getValue();
        int value2 = (int) spinner2.getValue();
        int remaining = max - value1;
        SpinnerNumberModel model2 = (SpinnerNumberModel) spinner2.getModel();
        model2.setMaximum(remaining);
        if (value2 > remaining) {
            spinner2.setValue(remaining);
        }
    }
    private void calculateTotalPrice(double pricePerNight, double pricePerChildPerNight) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(txt_startDate.getText(), formatter);
            LocalDate endDate = LocalDate.parse(txt_endDate.getText(), formatter);
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

            int adultCount = (int) spn_adult.getValue();
            int childCount = (int) spn_child.getValue();

            double totalPrice = (adultCount * pricePerNight + childCount * pricePerChildPerNight) * daysBetween;
            txt_totalPrice.setText(String.format("%.2f", totalPrice));
        } catch (Exception e) {
            // Handle parsing exceptions
            txt_totalPrice.setText("Error");
        }
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
