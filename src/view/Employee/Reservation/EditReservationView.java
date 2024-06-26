package view.Employee.Reservation;

import business.concrete.ReservationManager;
import business.concrete.RoomManager;
import business.services.IReservationService;
import business.services.IRoomService;
import core.utilities.helpers.ComboItem;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class EditReservationView extends JFrame {
    private JPanel container;
    private JLabel lbl_hotelName;
    private JFormattedTextField txt_startDate;
    private JFormattedTextField txt_endDate;
    private JTextField txt_nameSurname;
    private JTextField txt_idNumber;
    private JTextField txt_phone;
    private JTextField txt_email;
    private JLabel lbl_totalPrice;
    private JButton updateReservationButton;
    private JComboBox<ComboItem> cmb_roomType;
    private JSpinner spn_adult;
    private JSpinner spn_child;
    private Reservation reservation;
    private IReservationService reservationService;
    private IRoomService roomService;
    private Room room;
    private int firsRoom;

    public EditReservationView(Reservation reservation) {
        this.reservation = reservation;
        this.room = new Room();
        this.reservationService = new ReservationManager();
        this.roomService = new RoomManager();
        this.add(container);
        FrameHelper.setupFrame(this, 600, 555, "Alpay Tourism Agency");
        initializeRoomTypes();
        setupListeners();
        setupInitialValues();
        updateReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedRoomType = (ComboItem) cmb_roomType.getSelectedItem();
                room = roomService.getById(selectedRoomType.getKey());

                int totalPrice = Integer.parseInt(lbl_totalPrice.getText().replace(",", ""));
                reservation.setTotalPrice(totalPrice);
                reservation.setStartDate(LocalDate.parse(txt_startDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                reservation.setEndDate(LocalDate.parse(txt_endDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                reservation.setAdultCount((int) spn_adult.getValue());
                reservation.setChildCount((int) spn_child.getValue());
                reservation.setCustomerEmail(txt_email.getText());
                reservation.setCustomerName(txt_nameSurname.getText());
                reservation.setCustomerPhone(txt_phone.getText());
                reservation.setCustomerId(txt_idNumber.getText());
                if(reservation.getRoomId() != selectedRoomType.getKey()){
                    Room room1 = roomService.getById(reservation.getRoomId());
                    room1.setStock(room1.getStock() + 1);
                    roomService.update(room1);
                    reservation.setRoomId(room.getRoomId());
                    room.setStock(room.getStock() - 1);
                    roomService.update(room);

                }
                SuccessInformationResult result = reservationService.update(reservation);
                result.showMessageDialog();
                dispose();



            }
        });
    }


    private void initializeRoomTypes() {
        for (Room room : this.roomService.getRoomsByHotelId(reservation.getHotelId(), reservation.getRoomId())) {
            this.cmb_roomType.addItem(room.getComboItem());
        }
        cmb_roomType.setSelectedItem(reservation.getRoomId());
        firsRoom = reservation.getRoomId();
    }

    private void setupListeners() {
        cmb_roomType.addActionListener(e -> {
            if (cmb_roomType.getSelectedItem() != null) {
                ComboItem selectedRoomType = (ComboItem) cmb_roomType.getSelectedItem();
                room = roomService.getById(selectedRoomType.getKey());

                updateSpinnerModels(room.getBedCount());

                if (selectedRoomType.getKey() == firsRoom) {
                    setInitialValues();
                }
            }
        });

        spn_adult.addChangeListener(adultChangeListener);
        spn_child.addChangeListener(childChangeListener);
        txt_startDate.addPropertyChangeListener(startDateListener);
        txt_endDate.addPropertyChangeListener(endDateListener);
    }

    private void setupInitialValues() {
        // Remove ChangeListeners temporarily
        removeListeners();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txt_startDate.setText(reservation.getStartDate().format(formatter));
        txt_endDate.setText(reservation.getEndDate().format(formatter));
        txt_email.setText(reservation.getCustomerEmail());
        txt_idNumber.setText(reservation.getCustomerId());
        txt_phone.setText(reservation.getCustomerPhone());
        txt_nameSurname.setText(reservation.getCustomerName());
        lbl_hotelName.setText(reservation.getHotel().getHotelName());
        spn_child.setValue(reservation.getChildCount());
        spn_adult.setValue(reservation.getAdultCount());
        lbl_totalPrice.setText(String.valueOf(reservation.getTotalPrice()));

        // Add ChangeListeners back
        addListeners();
    }

    private void setInitialValues() {
        spn_child.setValue(reservation.getChildCount());
        spn_adult.setValue(reservation.getAdultCount());
        lbl_totalPrice.setText(String.valueOf(reservation.getTotalPrice()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txt_startDate.setText(reservation.getStartDate().format(formatter));
        txt_endDate.setText(reservation.getEndDate().format(formatter));
    }

    private void updateSpinnerModels(int bedCount) {
        SpinnerNumberModel adultModel = new SpinnerNumberModel(0, 0, bedCount, 1);
        spn_adult.setModel(adultModel);

        SpinnerNumberModel childModel = new SpinnerNumberModel(0, 0, bedCount, 1);
        spn_child.setModel(childModel);
    }

    private void calculateTotalPrice(double pricePerNight, double pricePerChildPerNight) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(txt_startDate.getText(), formatter);
            LocalDate endDate = LocalDate.parse(txt_endDate.getText(), formatter);
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

            int adultCount = (int) spn_adult.getValue();
            int childCount = (int) spn_child.getValue();
            if(adultCount != 0 && childCount != 0){

                double totalPrice = (adultCount * pricePerNight + childCount * pricePerChildPerNight) * daysBetween;
                lbl_totalPrice.setText(String.format("%.2f", totalPrice));
            }

        } catch (Exception e) {
            lbl_totalPrice.setText("Error");
        }
    }

    private void updateSpinnerMaximum(JSpinner spinner1, JSpinner spinner2, int max) {
        if (spinner1 != null && spinner2 != null) {
            int value1 = (int) spinner1.getValue();
            int value2 = (int) spinner2.getValue();
            int remaining = max - value1;

            SpinnerModel model2 = spinner2.getModel();
            if (model2 instanceof SpinnerNumberModel) {
                SpinnerNumberModel numberModel2 = (SpinnerNumberModel) model2;
                numberModel2.setMaximum(remaining);

                if (value2 > remaining) {
                    spinner2.setValue(remaining);
                }
            }
        }
    }


    private void removeListeners() {
        spn_adult.removeChangeListener(adultChangeListener);
        spn_child.removeChangeListener(childChangeListener);
        txt_startDate.removePropertyChangeListener(startDateListener);
        txt_endDate.removePropertyChangeListener(endDateListener);
    }

    private void addListeners() {
        spn_adult.addChangeListener(adultChangeListener);
        spn_child.addChangeListener(childChangeListener);
        txt_startDate.addPropertyChangeListener(startDateListener);
        txt_endDate.addPropertyChangeListener(endDateListener);
    }

    // Define ChangeListeners globally
    private ChangeListener adultChangeListener = e -> {
        if (room.getAdultPrice() != null) {
            updateSpinnerMaximum(spn_adult, spn_child, room.getBedCount());
            calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
        }
    };


    private ChangeListener childChangeListener = e -> {
        if (room.getChildPrice() != null) {
        updateSpinnerMaximum(spn_child, spn_adult, room.getBedCount());
        calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
    }
    };

    private PropertyChangeListener startDateListener = evt -> {
        if (room.getAdultPrice() != null && room.getChildPrice() != null) {
            calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
        }
    };

    private PropertyChangeListener endDateListener = evt -> {
        if (room.getAdultPrice() != null && room.getChildPrice() != null) {
            calculateTotalPrice(room.getAdultPrice(), room.getChildPrice());
        }
    };





    private void createUIComponents() throws ParseException {
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
