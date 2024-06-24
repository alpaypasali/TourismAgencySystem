package view.Employee.Reservation;

import business.concrete.RoomManager;
import business.services.IRoomService;
import entity.Hotel;

import javax.swing.*;

public class InfoHotelView extends JFrame{
    private JPanel container;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel lbl_hotelName;
    private JPanel pnl_roomType;
    private JComboBox comboBox1;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTextField textField5;
    private JButton createReservationButton;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JCheckBox checkBox7;
    private JCheckBox checkBox8;
    private Hotel hotel;
    private IRoomService roomService;
    public InfoHotelView(Hotel hotel){
        this.add(container);
        this.hotel = hotel;
        this.roomService = new RoomManager();
    }
}
