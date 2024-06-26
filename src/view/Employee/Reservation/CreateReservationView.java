package view.Employee.Reservation;

import business.concrete.ReservationManager;
import business.concrete.RoomManager;
import business.services.IReservationService;
import business.services.IRoomService;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Hotel;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateReservationView extends JFrame{
    private JPanel container;
    private JLabel lbl_hotelName;
    private JLabel lbl_adultCount;
    private JLabel lbl_childCount;
    private JTextField txt_nameSurname;
    private JTextField txt_phone;
    private JTextField txt_email;
    private JTextField txt_idNumber;
    private JLabel lbl_totalPrice;
    private JButton createReservationButton;
    private JLabel lbl_roomType;
    private JFormattedTextField txt_startDate;
    private JFormattedTextField txt_endDate;
    private Reservation reservation;
    private IReservationService reservationService;
    private IRoomService roomService;
    private Hotel hotel;
    private Room room;

    public CreateReservationView(Hotel hotel , Room room ,Reservation reservation ){
        this.add(container);
        this.reservation = reservation;
        this.hotel = hotel;
        this.room = room;
        this.reservationService = new ReservationManager();
        this.roomService = new RoomManager();

        FrameHelper.setupFrame(this,600, 555, "Alpay Tourism Agency");

        lbl_hotelName.setText(hotel.getHotelName());
        lbl_roomType.setText(room.getRoomType().getName());
        txt_startDate.setText(reservation.getStartDate().toString());
        txt_endDate.setText(reservation.getEndDate().toString());
        lbl_adultCount.setText(String.valueOf(reservation.getAdultCount()));
        lbl_childCount.setText(String.valueOf(reservation.getChildCount()));
        lbl_totalPrice.setText(String.valueOf(reservation.getTotalPrice()));


        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservation.setCustomerName(txt_nameSurname.getText());
                reservation.setCustomerId(txt_idNumber.getText());
                reservation.setCustomerPhone(txt_phone.getText());
                reservation.setCustomerEmail(txt_email.getText());
                reservation.setHotelId(hotel.getHotelId());
                reservation.setRoomId(room.getRoomId());

                SuccessInformationResult result = reservationService.create(reservation);
                result.showMessageDialog();
                room.setStock(room.getStock() - 1);
                roomService.update(room);
                dispose();

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
