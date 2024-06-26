package view.Employee.Hotel;

import business.concrete.HotelManager;
import business.concrete.PensionTypeManager;
import business.services.IHotelService;
import business.services.IPensionTypeService;
import core.utilities.helpers.ComboItem;
import core.utilities.helpers.FrameHelper;
import core.utilities.results.SuccessInformationResult;
import entity.Hotel;
import entity.PensionType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateHotelView extends JFrame {
    private JPanel container;
    private JTextField txt_hotelname;
    private JTextField txt_city;
    private JTextField txt_district;
    private JTextField txt_email;
    private JTextField txt_phone;
    private JTextArea txt_fullAddress;
    private JCheckBox SPACheckBox;
    private JCheckBox roomServiceCheckBox;
    private JCheckBox freeParkingCheckBox;
    private JComboBox cmb_stars;
    private JComboBox cmb_pensionType;
    private JButton updateButton;
    private Hotel hotel;
    private IHotelService hotelService;
    private IPensionTypeService pensionTypeService;

    public UpdateHotelView(Hotel hotel){
        this.add(container);
        this.hotel = hotel;
        this.hotelService = new HotelManager();
        this.pensionTypeService = new PensionTypeManager();
        Integer[] starsArray = {1, 2, 3, 4, 5};
        for (Integer stars : starsArray) {
            this.cmb_stars.addItem(stars);
        }

        FrameHelper.setupFrame(this,989, 555, "Alpay Tourism Agency");

        for (PensionType pensionType : this.pensionTypeService.getAll())
        {
            this.cmb_pensionType.addItem(new ComboItem(pensionType.getId(),pensionType.getName()));
        }
        cmb_pensionType.setSelectedItem(hotel.getPensionTypeId());
        cmb_stars.setSelectedItem(hotel.getStarRating());
        txt_hotelname.setText(hotel.getHotelName());
        txt_email.setText(hotel.getEmail());
        txt_phone.setText(hotel.getPhone());
        txt_city.setText(hotel.getCity());
        txt_district.setText(hotel.getDistrict());
        txt_fullAddress.setText(hotel.getFullAddress());
        roomServiceCheckBox.setSelected(hotel.getHas24_7RoomService());
        freeParkingCheckBox.setSelected(hotel.getHasFreeParking());
        SPACheckBox.setSelected(hotel.getHasSpa());



        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedPensionType = (ComboItem) cmb_pensionType.getSelectedItem();
                hotel.setHotelName(txt_hotelname.getText());
                hotel.setEmail(txt_email.getText());
                hotel.setPhone(txt_phone.getText());
                hotel.setCity(txt_city.getText());
                hotel.setDistrict(txt_district.getText());
                hotel.setFullAddress(txt_fullAddress.getText());
                hotel.setStarRating((Integer) cmb_stars.getSelectedItem());
                hotel.setHas24_7RoomService(roomServiceCheckBox.isSelected());
                hotel.setHasFreeParking(freeParkingCheckBox.isSelected());
                hotel.setHasSpa(SPACheckBox.isSelected());
                hotel.setPensionTypeId(selectedPensionType.getKey());
                SuccessInformationResult createdHotel = hotelService.update(hotel);
                createdHotel.showMessageDialog();
                dispose();
            }
        });
    }
}
